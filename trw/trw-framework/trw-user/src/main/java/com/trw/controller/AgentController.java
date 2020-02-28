package com.trw.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.trw.annotion.Permission;
import com.trw.constant.Constant;
import com.trw.factory.PageFactory;
import com.trw.feign.MsgFeignApi;
import com.trw.manage.ShiroKit;
import com.trw.model.ServiceAgentRole;
import com.trw.model.TrwTAgent;
import com.trw.model.TrwTFaci;
import com.trw.service.IAgentService;
import com.trw.service.IFaciService;
import com.trw.service.ServiceAgentRoleService;
import com.trw.service.impl.RedisService;
import com.trw.util.BusinessUtil;
import com.trw.util.MD5Util;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.NoticeMsg;
import com.trw.vo.ResultMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 经纪人controller
 * </p>
 *
 * @author linhai123
 * @since 2018-06-14
 */
@RestController
public class AgentController extends BaseController {

	@Autowired
	private IAgentService agentservice;
	@Autowired
	private RedisService redisService;
	@Autowired
	private IFaciService iFaciService;
	@Autowired
	private MsgFeignApi producer;
	@Autowired
	private ServiceAgentRoleService serviceAgentRoleService;

	// 登录
	@RequestMapping(value = "/alogin")
	@ApiOperation(httpMethod = "POST", value = "登录", notes = "输入登录号和密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<TrwTAgent> login(HttpServletRequest req) {
		ResultMsg<TrwTAgent> resultMsg = new ResultMsg<>();
		String account = req.getParameter("account");
		String pwd = req.getParameter("pwd");

		if (StrKit.isBlank(account)) {
			resultMsg.setMsg("用户名不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		
		TrwTAgent agent  =null;

		if (StrKit.isBlank(pwd)) {
			resultMsg.setMsg("密码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(account, pwd);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			agent = (TrwTAgent) subject.getPrincipal();
		} catch (DisabledAccountException e) {
			resultMsg.setMsg("账户已被禁用");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			resultMsg.setMsg("用户名或密码错误");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		
		resultMsg.setCode(Constant.CODE_SUCC);
		agent.setIdcard(agent.getIdcard().replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1****$2"));
		agent.setAphone(agent.getAphone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		resultMsg.setData(agent);
		return resultMsg;
	}

	@RequestMapping(value = "/getAgentById", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "POST", value = "根据经纪人id查询经纪人", notes = "根据经纪人id查询经纪人")
	public ResultMsg<TrwTAgent> getAgentById(@RequestParam("id") String id) {
		ResultMsg<TrwTAgent> result = new ResultMsg<TrwTAgent>();
		TrwTAgent faci = agentservice.selectById(id);
		result.setCode(Constant.CODE_SUCC);
		result.setData(faci);
		return result;
	}
	
	@Permission
	@RequestMapping(value = "/auth/empManage", method = RequestMethod.GET)
	@ApiOperation(value = "服务中心-我的账户-员工管理-员工列表", notes = "服务中心-我的账户-员工管理-员工列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "limit", value = "每页多少条数据", dataType = "string", paramType = "query") })
	public ResultMsg<Map<String, Object>> empManage(HttpServletRequest req) {
		Map<String, String> param = getParamMapFromRequest(req);
		ResultMsg<Map<String, Object>> resultMsg = new ResultMsg<>();

		TrwTAgent agent = ShiroKit.getUser();
		if (StrKit.isBlank(agent.getFaciid())) {
			resultMsg.setMsg("该经纪人对应的服务商id为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		param.put("faciid", agent.getFaciid());
		param.put("agentId", agent.getId());

		Page<TrwTAgent> page = new PageFactory<TrwTAgent>().defaultPage();
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = agentservice.selectByfaciid(page, param);
		map.put("agentList", list);
		map.put("page", page);
		resultMsg.setData(map);
		resultMsg.setMsg("查询员工成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/resetPwd")
	@ApiOperation(httpMethod = "POST", value = "服务中心-我的账户-员工管理-重置密码", notes = "服务中心-我的账户-员工管理")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "agentId", value = "agentId", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> resetPwd(HttpServletRequest req) {
		TrwTAgent user = ShiroKit.getUser();
		TrwTAgent entity = agentservice.selectById(req.getParameter("agentId"));
		entity.setApwd(MD5Util.encrypt("111111"));
		entity.setOperator(user.getId());
		entity.setOperatorTime(LocalDateTime.now());
		agentservice.updateById(entity);
		ResultMsg<String> resultMsg = new ResultMsg<>();
		resultMsg.setMsg("重置成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateaStat")
	@ApiOperation(httpMethod = "POST", value = "服务中心-我的账户-员工管理-冻结经纪人", notes = "服务中心-我的账户-员工管理-冻结经纪人")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "agentId", value = "agentId", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "astat", value = "astat", required = true, dataType = "string", paramType = "query")})
	public ResultMsg<String> updateaStat(HttpServletRequest req) {
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		TrwTAgent agent = ShiroKit.getUser();
		String agentId = req.getParameter("agentId");
		//根据经纪人id查询角色信息
		Wrapper<ServiceAgentRole> wrapper = new EntityWrapper<ServiceAgentRole>().eq("agentId",agentId);
		ServiceAgentRole role = serviceAgentRoleService.selectOne(wrapper);
		String roleId = role.getRoleId();
		if (roleId.equals("1")) {
			resultMsg.setMsg("不能冻结服务中心");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTAgent entity = new TrwTAgent();
		entity.setId(agentId);
		entity.setOperator(agent.getId());
		entity.setOperatorTime(LocalDateTime.now());
		String astat = req.getParameter("astat");
		if (StrKit.equals("01", astat)) {
			entity.setAstat("02");
		} else {
			entity.setAstat("01");
		}
		agentservice.updateById(entity);
		resultMsg.setMsg("冻结成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateaAgent")
	@ApiOperation(httpMethod = "POST", value = "服务中心-我的账户-员工管理-修改员工", notes = "服务中心-我的账户-员工管理-修改员工")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "agentId", value = "修改的经纪人id",dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "aname", value = "经纪人姓名", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "aphone", value = "手机", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "portrait", value = "经纪人头像", dataType = "string", paramType = "query"),
		})
	public ResultMsg<String> updateaAgent(HttpServletRequest req) {
		ResultMsg<String> rmg = new ResultMsg<>();
		TrwTAgent agent = new TrwTAgent();
		TrwTAgent agents = ShiroKit.getUser();
		agent.setId(req.getParameter("agentId"));
		agent.setAname(req.getParameter("aname"));
		agent.setSex(req.getParameter("sex"));
		agent.setPortrait(req.getParameter("portrait"));
		
		String aphone = req.getParameter("aphone");
		agent.setAphone(aphone);
		agent.setOperatorTime(LocalDateTime.now());
		agent.setOperator(agents.getId());
		String roleId = req.getParameter("roleId");
		
		Boolean flg = agentservice.updateaAgent(agent,roleId);
		if (flg) {
			rmg.setMsg("修改成功");
			rmg.setCode(Constant.CODE_SUCC);
		} else {
			rmg.setMsg("修改失败");
			rmg.setCode(Constant.CODE_FAIL);
		}
		return rmg;
	}

	@RequestMapping(value = "validServicePhone")
	@ApiOperation(httpMethod = "POST", value = "根据手机查看是否注册过(短信找回密码)", notes = "根据手机查看是否注册过(短信找回密码)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "aphone", value = "电话", required = true, dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTAgent> findAgentByPhone(HttpServletRequest req) {
		ResultMsg<TrwTAgent> resultMsg = new ResultMsg<TrwTAgent>();
		String aphone = req.getParameter("aphone");
		Wrapper<TrwTAgent> wrapper = new EntityWrapper<TrwTAgent>().eq("aphone", aphone);
		TrwTAgent agent = agentservice.selectOne(wrapper);
		if (agent != null) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(agent);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("手机未注册");
		return resultMsg;
	}

	@RequestMapping(value = "updatePwdByPhone")
	@ApiOperation(httpMethod = "POST", value = "根据手机修改密码", notes = "根据手机修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "aphone", value = "电话", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newApwd", value = "新密码", dataType = "string", required = true, paramType = "query"), })
	public ResultMsg<TrwTAgent> updatePwdByPhone(HttpServletRequest req) {
		ResultMsg<TrwTAgent> resultMsg = new ResultMsg<TrwTAgent>();
		String aphone = req.getParameter("aphone");
		String newApwd = req.getParameter("newApwd");
		TrwTAgent agents = ShiroKit.getUser();
		TrwTAgent agent = new TrwTAgent();
		agent.setAphone(aphone);
		agent.setApwd(MD5Util.encrypt(newApwd));
		agent.setOperator(agents.getId());
		agent.setOperatorTime(LocalDateTime.now());
		if (agentservice.updateByPhone(agent) > 0) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setMsg("修改成功");
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setMsg("修改失败");
		return resultMsg;
	}

	@RequestMapping(value = "/auth/addEmp")
	@ApiOperation(httpMethod = "POST", value = "服务中心-员工管理-添加员工", notes = "服务中心-员工管理-添加员工")
	@ApiImplicitParams({ @ApiImplicitParam(name = "aname", value = "员工姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "aphone", value = "手机号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "idcard", value = "身份证", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "portrait", value = "头像", dataType = "string", paramType = "query") })
	public ResultMsg<String> addEmp(HttpServletRequest req) {
		ResultMsg<String> rmg = new ResultMsg<>();
		TrwTAgent agents = ShiroKit.getUser();
		// 设置员工属性
		TrwTAgent agent = new TrwTAgent();
		String agentId = ToolUtil.generateId("AGT");
		agent.setId(agentId);
		agent.setAname(req.getParameter("aname"));
		agent.setSex(req.getParameter("sex"));

		// 查询员工是否存在
		String phone = req.getParameter("aphone");
		Wrapper<TrwTAgent> wrapper = new EntityWrapper<TrwTAgent>().eq("aphone", phone);
		TrwTAgent selectOne = agentservice.selectOne(wrapper);
		if (selectOne != null) {
			rmg.setCode(Constant.CODE_FAIL);
			rmg.setMsg("手机号已经被注册");
			return rmg;
		}
		// 设置初始用户名及密码
		agent.setAacount(phone);
		agent.setApwd(MD5Util.encrypt("123456"));

		String faciid = req.getParameter("faciid");
		if (faciid == null) {
			rmg.setMsg("没有传入服务商id");
			rmg.setCode(Constant.CODE_FAIL);
			return rmg;
		}
		agent.setFaciid(faciid);
		agent.setAphone(phone);
		agent.setIdcard(req.getParameter("idcard"));
		// 默认状态1启用
		agent.setAstat(Constant.YES);
		agent.setPortrait(req.getParameter("portrait"));
		agent.setCreateTime(new Date());
		
		//设置操作人与操作时间
		agent.setOperator(agents.getId());
		agent.setOperatorTime(LocalDateTime.now());

		// 设置角色
		ServiceAgentRole role = new ServiceAgentRole();
		// 经纪人权限表与经纪人表经纪人id类型长度不对应，暂时设置为"1"
		role.setArId(ToolUtil.generateId("TAR"));
		role.setAgentId(agentId);
		String roleId = req.getParameter("roleId");
		role.setRoleId(roleId);
		role.setRemark("服务中心手动添加员工");

		agentservice.insertAgentInfo(agent, role);

		rmg.setCode(Constant.CODE_SUCC);
		rmg.setMsg("添加成功");

		return rmg;
	}

	@RequestMapping(value = "/auth/applyToAgent")
	@ApiOperation(httpMethod = "POST", value = "申请成为经纪人", notes = "申请成为经纪人")
	@ApiImplicitParams({ @ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "申请人姓名", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "申请人电话号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "验证码", dataType = "string", paramType = "query") })
	public ResultMsg<String> applyToAgent(HttpServletRequest req) {
		String faciid = req.getParameter("faciid");
		String name = req.getParameter("name");
		String phone = req.getParameter("phone");
		String code = req.getParameter("code");
		ResultMsg<String> resultMsg = new ResultMsg<>();

		if (validSmsCode(phone, code)) {
			TrwTFaci faci = iFaciService.selectById(faciid);
			try {
				if (faci == null) {
					// 给平台发信息通知
					faci = BusinessUtil.getPlatFaci();
				}
				JSONObject smsJson = new JSONObject();
				smsJson.put("name", faci.getContacts());
				smsJson.put("name2", name);
				smsJson.put("tel", phone);
				String template = smsJson.toJSONString();
				// 处理消息
				String applyAgent = redisService.getString(Constant.CONFIGPIX + "applyAgent");
				NoticeMsg msg = new NoticeMsg();
				msg.setMsgId(applyAgent);
				msg.setPhone(faci.getMobile());
				msg.setParam(template);
				msg.setUserId(faci.getFaciid());
				msg.setNeedSMS(Constant.YES);
				producer.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
				resultMsg.setMsg(e.getMessage());
				resultMsg.setCode(Constant.CODE_FAIL);
				return resultMsg;
			}
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setMsg("申请成功");
			return resultMsg;
		} else {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("短信验证错误");
			return resultMsg;
		}
	}

	@RequestMapping(value = "/updateAgentById")
	@ApiOperation(httpMethod = "POST", value = "修改经纪人信息", notes = "修改经纪人信息")
	public ResultMsg<TrwTAgent> updateAgentById(@RequestBody TrwTAgent agent) {
		ResultMsg<TrwTAgent> resultMsg = new ResultMsg<>();
		TrwTAgent agents = ShiroKit.getUser();
		agent.setOperatorTime(LocalDateTime.now());
		agent.setOperator(agents.getId());
		
		if (agentservice.updateById(agent)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(agent);
			return resultMsg;
		}
		resultMsg.setMsg("修改失败");
		resultMsg.setCode(Constant.CODE_FAIL);
		return resultMsg;
	}

	@RequestMapping(value = "/selectFaicAgent")
	@ApiOperation(httpMethod = "POST", value = "查询服务商下面的经纪人", notes = "查询服务商下面的经纪人")
	@ApiImplicitParam(name = "faciid", value = "服务商id", dataType = "string", paramType = "query")
	public ResultMsg<List<Map<String, Object>>> selectFaicAgent(HttpServletRequest req) {
		Page<TrwTAgent> page = new PageFactory<TrwTAgent>().defaultPage();
		List<Map<String, Object>> selectFaicAgent = agentservice.selectAgent(page, req.getParameter("faciid"));
		ResultMsg<List<Map<String, Object>>> resultMsg = new ResultMsg<>();
		resultMsg.setMsg("查询成功");
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(selectFaicAgent);
		return resultMsg;
	}

	@RequestMapping(value = "/auth/updateAgentPwdById")
	@ApiOperation(httpMethod = "POST", value = "修改登录密码", notes = "修改登录密码")
	@ApiImplicitParams({ @ApiImplicitParam(name = "aphone", value = "电话号码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "验证码", dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newApwd", value = "新密码", dataType = "string", paramType = "query"), })
	public ResultMsg<TrwTAgent> updateAgentPwdById(HttpServletRequest req) {
		ResultMsg<TrwTAgent> resultMsg = new ResultMsg<>();
		TrwTAgent agents = ShiroKit.getUser();
		String aphone = req.getParameter("aphone");
		String code = req.getParameter("code");
		String newApwd = req.getParameter("newApwd");
		if (StrKit.isBlank(aphone)) {
			resultMsg.setMsg("电话不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		TrwTAgent agent = agentservice.selectById(agents.getId());
		if (StrKit.isBlank(agent.getAphone())) {
			resultMsg.setMsg("该经纪人电话为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (!StrKit.equals(aphone, agent.getAphone())) {
			resultMsg.setMsg("该经纪人注册的手机号与您输的手机号不一致");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(code)) {
			resultMsg.setMsg("验证码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (StrKit.isBlank(newApwd)) {
			resultMsg.setMsg("新密码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		if (!validSmsCode(aphone, code)) {
			resultMsg.setCode(Constant.CODE_FAIL);
			resultMsg.setMsg("短信验证失败");
		}
		agent.setApwd(MD5Util.encrypt(newApwd));
		agent.setOperator(agents.getId());
		agent.setOperatorTime(LocalDateTime.now());
		if (agentservice.updateById(agent)) {
			resultMsg.setCode(Constant.CODE_SUCC);
			resultMsg.setData(agent);
			return resultMsg;
		}
		resultMsg.setCode(Constant.CODE_FAIL);
		resultMsg.setData(null);
		return resultMsg;
	}
	
	/**
	 * 
	* @Title: logout 
	* @Description: 退出
	* @author jianglingyun
	* @param @return  参数说明 
	* @return ResultMsg<String> 返回类型 
	* @throws 
	* @date 2018年8月17日
	 */
	@RequestMapping(value = "/auth/logout")
	public ResultMsg<String> logout() {
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		Subject subject = SecurityUtils.getSubject();
	    subject.logout();
	    resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setMsg("用户已退出");
		return resultMsg;

	}
	
	

}
