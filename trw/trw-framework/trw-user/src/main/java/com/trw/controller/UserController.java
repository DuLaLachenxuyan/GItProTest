package com.trw.controller;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.trw.constant.Constant;
import com.trw.factory.LogTaskFactory;
import com.trw.manage.MuiThreadManager;
import com.trw.model.TrwTUser;
import com.trw.service.IUserService;
import com.trw.util.HttpKit;
import com.trw.util.MD5Util;
import com.trw.util.StrKit;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;
import com.trw.vo.TokenData;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: UserController
 * @Description: 用户控制器
 * @author luojing
 * @date 2018年7月4日 上午10:15:15
 *
 */
@RestController
public class UserController extends BaseController {

	@Autowired
	private IUserService userService;
	@Autowired
	private DefaultKaptcha captchaProducer;

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "登录", notes = "输入登录号和密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账号", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<TrwTUser> login(HttpServletRequest req) {
		ResultMsg<TrwTUser> resultMsg = new ResultMsg<>();
		String account = req.getParameter("account");
		String pwd = req.getParameter("pwd");

		if (StrKit.isBlank(account)) {
			resultMsg.setMsg("用户名不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		if (StrKit.isBlank(pwd)) {
			resultMsg.setMsg("密码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		TrwTUser temp = new TrwTUser();
		temp.setAccount(account);
		TrwTUser user = null;
		List<TrwTUser> list = userService.selectList(new EntityWrapper<>(temp));
		if (!list.isEmpty()) {
			user = list.get(0);
			String pwdMd5 = MD5Util.encrypt(pwd);

			if (pwdMd5.equals(user.getPwd())) {
				String sessionId = req.getSession().getId();
				redisService.set(Constant.SESSIONPIX + sessionId, list.get(0).getUserid(), 1800L, TimeUnit.SECONDS);
				resultMsg.setCode(Constant.CODE_SUCC);
				resultMsg.setData(user);
				resultMsg.setToken(sessionId);
			} else {
				resultMsg.setMsg("密码错误");
				resultMsg.setCode(Constant.CODE_FAIL);
				MuiThreadManager.me().executeLog(LogTaskFactory.loginLog(user.getUserid(), "密码错误", HttpKit.getIp()));
				return resultMsg;
			}

		} else {
			resultMsg.setMsg("用户不存在");
			resultMsg.setCode(Constant.CODE_FAIL);
		}
		return resultMsg;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "注册", notes = "输入登录号和密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone", value = "账号", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "短信验证码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<TrwTUser> register(HttpServletRequest req) {
		ResultMsg<TrwTUser> resultMsg = new ResultMsg<>();
		String phone = req.getParameter("phone");
		String pwd = req.getParameter("pwd");
		String code = req.getParameter("code");
		boolean valid = validSmsCode(phone, code);

		if (!valid) { // 短信验证码错误
			resultMsg.setMsg("短信验证码错误");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		// 通过电话号码查询用户
		Integer result = userService.selectByphone(phone);
		// 判断用户是否存在
		if (result != null) {
			resultMsg.setMsg("用户已经存在");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		String pwdMd5 = MD5Util.encrypt(pwd);
		TrwTUser user = new TrwTUser();
		user.setUserid(ToolUtil.generateId("USR"));
		user.setPhone(phone);
		user.setAccount(phone);
		user.setPwd(pwdMd5);
		user.setCreatetime(new Date());
		user.setStatus(1);
		user.setName(phone);
		userService.insert(user);
		// 用户登录
		String sessionId = req.getSession().getId();
		redisService.set(Constant.SESSIONPIX + sessionId, user.getUserid(), 1800L, TimeUnit.SECONDS);
		resultMsg.setToken(sessionId);
		resultMsg.setData(user);
		resultMsg.setCode(Constant.CODE_SUCC);
		return resultMsg;
	}

	@RequestMapping(value = "/captcha")
	@ApiOperation(httpMethod = "POST", value = "图片验证码", notes = "图片验证码")
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");

		response.setContentType("image/jpeg");

		String capText = captchaProducer.createText();
		try {
			String uuid = UUID.randomUUID().toString();
			// RedisTempUtil.setTime(uuid, capText,60*5L,TimeUnit.SECONDS);
			Cookie cookie = new Cookie("captchaCode", uuid + ":" + capText);
			cookie.setPath("/");
			// response.setHeader("captchaCode", uuid+":"+capText);
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}

		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	@RequestMapping(value = "/validPhone")
	@ApiOperation(httpMethod = "POST", value = "校验手机号是否被注册", notes = "校验手机号是否被注册")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> validPhone(String phone) {
		ResultMsg<String> msg = new ResultMsg<String>();
		Integer result = userService.selectByphone(phone);
		// 判断用户是否存在
		if (result != null) {
			msg.setMsg("手机号已注册");
			msg.setCode(Constant.CODE_FAIL);
			return msg;
		}
		msg.setCode(Constant.CODE_SUCC);
		return msg;

	}

	@RequestMapping(value = "/guest/updatepwd", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "根据当前密码修改密码", notes = "修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "oldPwd", value = "用户输入的旧密码", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "newPwd", value = "新密码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> updatePwd(HttpServletRequest req, @RequestParam String oldPwd,
			@RequestParam String newPwd) {
		// 实例化结果对象
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		// 获取登陆用户
		TokenData tokenData = (TokenData) req.getAttribute("tokenData");
		// 判断是否登陆
		if (tokenData == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}

		String userId = tokenData.getUserId();
		TrwTUser user = userService.selectById(userId);
		// 获取到源密码
		String pwd = user.getPwd();

		// 判断用户输入原密码是否为空
		if (StrKit.isBlank(oldPwd)) {
			resultMsg.setMsg("原密码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 将用户输入的旧密码MD5加密
		String pwdMD5 = MD5Util.encrypt(oldPwd);
		// 判断原密码是否一致
		if (!pwd.equals(pwdMD5)) {
			resultMsg.setMsg("原密码不正确");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 判断用户输入新密码是否为空
		if (StrKit.isBlank(newPwd)) {
			resultMsg.setMsg("新密码不能为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 将新密码设置为MD5
		user.setPwd(MD5Util.encrypt(newPwd));
		// 更新密码
		userService.updateById(user);
		resultMsg.setMsg("修改成功");
		resultMsg.setCode(Constant.CODE_SUCC);

		return resultMsg;
	}

	/**
	 * 验证手机验证码
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "验证身份")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "valiCode", value = "短信验证码", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> authentication(HttpServletRequest req) {
		// 实例结果对象
		ResultMsg<String> resultMsg = new ResultMsg<String>();

		// 获取页面参数
		String phone = req.getParameter("phone");
		String code = req.getParameter("valiCode");
		// 判断是否为空输入项
		if (StrKit.isBlank(code)) {
			resultMsg.setMsg("短信验证码为空");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 校验验证码
		if (validSmsCode(phone, code)) {
			resultMsg.setMsg("身份验证成功");
			resultMsg.setCode(Constant.CODE_SUCC);
		} else {
			resultMsg.setMsg("短信验证码错误");
			resultMsg.setCode(Constant.CODE_FAIL);
		}
		return resultMsg;
	}

	@RequestMapping(value = "/guest/updatePwdbynote", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "验证身份")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "newPwd", value = "用户输入的新密码", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "rePwd", value = "用户输入的新密码", required = true, dataType = "string", paramType = "query") })
	public ResultMsg<String> updatePwdbynote(HttpServletRequest req) {
		// 实例结果对象
		ResultMsg<String> resultMsg = new ResultMsg<String>();
		// 判断是否登陆
		TokenData td = (TokenData) req.getAttribute("tokenData");
		String uid = td.getUserId();
		if (uid == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 根据uid获取用户
		TrwTUser user = userService.selectById(uid);
		// 获取页面参数
		String newPwd = req.getParameter("newPwd");
		String rePwd = req.getParameter("rePwd");

		// 判断是否为空输入项
		if (StrKit.isBlank(newPwd) && StrKit.isBlank(rePwd)) {
			resultMsg.setMsg("存在空白输入项");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 验证两次输入密码是否一致
		if (!newPwd.equals(rePwd)) {
			resultMsg.setMsg("两次密码输入不一致");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		} else {
			String pwd = MD5Util.encrypt(newPwd);
			user.setPwd(pwd);
			userService.updateById(user);
			resultMsg.setMsg("修改成功");
			resultMsg.setCode(Constant.CODE_SUCC);
		}
		return resultMsg;
	}

	@RequestMapping(value = "/guest/getUserInfo", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "获取个人信息")
	public ResultMsg<TrwTUser> getUserInfo(HttpServletRequest req) {
		// 实例结果对象
		ResultMsg<TrwTUser> resultMsg = new ResultMsg<>();
		// 判断是否登陆
		TokenData td = (TokenData) req.getAttribute("tokenData");
		String uid = td.getUserId();
		if (uid == null) {
			resultMsg.setMsg("请先登录");
			resultMsg.setCode(Constant.CODE_FAIL);
			return resultMsg;
		}
		// 根据uid获取用户
		TrwTUser user = userService.selectById(uid);
		resultMsg.setCode(Constant.CODE_SUCC);
		resultMsg.setData(user);

		return resultMsg;
	}

	@RequestMapping(value = "/getUser")
	@ApiOperation(httpMethod = "POST", value = "获取用户信息", notes = "获取用户信息")
	public ResultMsg<TrwTUser> getUser(@RequestParam("userId") String userId) {
		ResultMsg<TrwTUser> result = new ResultMsg<>();
		result.setCode(Constant.CODE_SUCC);
		result.setData(userService.getUser(userId));
		return result;
	}

	/**
	 * @Title: updateUser
	 * @Description: 修改用户信息
	 * @author luojing
	 * @param @param
	 *            user
	 * @param @return
	 *            参数说明
	 * @return ResultMsg<String> 返回类型
	 * @throws @date
	 *             2018年7月16日
	 */
	@RequestMapping(value = "/guest/updateUser", method = RequestMethod.POST, consumes = { "application/json" })
	@ApiOperation(httpMethod = "POST", value = "修改用户信息", notes = "修改用户信息")
	public ResultMsg<String> updateUser(@RequestBody TrwTUser user) {
		ResultMsg<String> result = new ResultMsg<String>();
		// 判断是否登陆
		TokenData td = getTokenData();
		if (td == null) {
			result.setCode(Constant.CODE_FAIL);
			result.setMsg("请先登录");
		} else {
			if (user != null) {
				user.setUserid(td.getUserId());
				boolean fag = userService.updateById(user);
				if (fag) {
					result.setCode(Constant.CODE_SUCC);
				} else {
					result.setCode(Constant.CODE_FAIL);
				}
			} else {
				result.setCode(Constant.CODE_FAIL);
			}
		}
		return result;
	}

	@RequestMapping(value = "/checkPhoneCodeLogin")
	@ApiOperation(httpMethod = "POST", value = "用户忘记密码或者想用手机验证码登录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "code", value = "短信验证码", dataType = "string", paramType = "query"), })
	public ResultMsg<String> checkPhoneCodeLogin(HttpServletRequest req) {
		String phone = request.getParameter("phone");
		String code = request.getParameter("code");
		ResultMsg<String> msg = new ResultMsg<String>();
		Integer result = userService.selectByphone(phone);
		// 判断用户是否存在
		if (result == null) {
			msg.setMsg("用户未注册,请先注册");
			msg.setCode(Constant.CODE_FAIL);
			return msg;
		}
		if (!validSmsCode(phone, code)) { // 短信验证码错误
			msg.setMsg("短信验证码错误");
			msg.setCode(Constant.CODE_FAIL);
			return msg;
		}
		msg.setCode(Constant.CODE_SUCC);
		msg.setMsg("手机和短信校验码通过,可以登录");
		return msg;
	}
}
