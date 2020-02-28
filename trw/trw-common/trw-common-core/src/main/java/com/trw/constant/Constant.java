package com.trw.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static final String YES="01";
	public static final String NO="02";
	
	//session前缀
	public static final String SESSIONPIX="user:session:";
	
	public static final String AGENTPIX="agent:session:";
	//字典前缀
	public static final String DICPIX="dict:";
	//配置参数前缀
	public static final String CONFIGPIX="config:";
	//省市县缓存
	public static final String PCAPIX="pca:";
	//服务中心缓存
	public static final String FACI="faci:";
	
	//消息结构
	public static final String CODE="code";
	public static final String MSG="msg";
	public static final String DATA="data";
	//消息成功
	public static final String CODE_SUCC="200";
	//消息失败
	public static final String CODE_FAIL="400";
	//
	public static final String OVER_TIME="700";
	
	public static final String ORDER_PAYING="01";//待付款
	public static final String ORDER_PAYED="02";//已支付
	public static final String ORDER_FINISH="03";//已完成
	public static final String ORDER_EVAL="04";//已评价 
	public static final String ORDER_CANCEL="05";//已取消
	
	
	/**
	 * 新闻页面缓存
	 */
	public static final String NEWSHOME="news:home";
	public static final String NEWSTYPES="news:types"; 
	/**
	 * 看地模式02-多次带看，03-保证金带看
	 */
	public static final String MANY_TIMES="02";//多次带看
	public static final String DEP_TIME="03";//保证金带看
	/**
	 * 资金流向
	 */
	public static final String INTTPUT = "01";//收入
	public static final String OUTPUT = "02";//支出
	/**
	 * 资金类型01-普通资金,02-保证金
	 */
	public static final String NORMAL = "01";//01-普通资金
	public static final String DEPOSIT = "02";//02-保证金
	
	/**
	 * 短信发送信息
	 * 土地信息
	 * 求购信息
	 */
	public static final String LANDMSG = "土地信息";
	public static final String REQUIREMSG = "求购信息";
	
	/**
	 * 短信模板
	 */
	public static Map<String,String> SMSTEMPLATEMAP = new HashMap<String, String>(){ 
		private static final long serialVersionUID = -4538011662817864158L;
		{  
	      put("SMS_137670681","您的动态码为：${code}，有效期为15分钟。温馨提示：您正在进行身份验证操作，如非本人操作，请忽略本短信！");      
	      put("SMS_137685924","${name}您好！来自于${region}地区、用户${name2}、联系电话${tel}发布了${demand}、请尽快查看处理。");      
	      put("SMS_137665946","${name}您好！接收到来自于${region}服务中心分配的带看订单，联系人${name2}联系电话${tel}、请及时处理。");      
	      put("SMS_137655957","${name}您好！来自于${region}地区的${name2}联系电话${tel}预约地块${project}、请尽快分配经纪人。");      
	      put("SMS_137665976","尊敬的${name}您好！你发布的${product}信息已分配经纪人${name2}联系电话${tel}");      
	      put("SMS_137666001","尊敬的${name}您好！由${name2}带看订单已完成，请及时给予我们经纪人评价，谢谢");      
	      put("cancel","${name}您好！来自于${region}地区的${name2},联系电话${tel}取消了订单");
	      put("customerAndLand","尊敬的${name}，已匹配到与您寻找的类似土地，请登录个人中心页面查看。");
			put("applyAgent","尊敬的${name}先生您好，接受到用户${name2}联系电话${tel}申请了经纪人请及时进行沟通处理。");
	}};
	
	public static String PREFIXLDP ="T"; //土地主键前缀
	public static String PREFIXLCP ="X"; // 寻找客户主键前缀
	
	
	//账户状态
	public static String ASTAT_NORMAL ="01"; // 正常
	public static String ASTAT_FREEZE ="02"; // 冻结
	
	//需求状态
	public static final String CLIENT_APPROVE = "01"; //待审核
	public static final String CLIENT_PAYING = "02";  //待交易
	public static final String CLIENT_FINISH= "03";  //已交易
	public static final String CLIENT_UNAPPROVE= "04";  //审核未通过
	public static final String CLIENT_STOP= "05";  //暂停
	public static final String CLIENT_AGENCY= "06";  //信息被代理
	
	//地源情况标签
	public static final String EARTH_UPLAND = "08"; //刷新土地
	public static final String EARTH_PAUSE = "04";  //暂停
	public static final String EARTH_APPROVE = "10";  //核实

	
	
	//收藏类型
	public static String ENSHRINE_LAND ="1";
	public static String ENSHRINE_NEWS ="2";
	public static String Enshrine_CLIENT ="3";

	
	//服务中心管理员角色
	public static String ROLE_ADMIN ="1";
	
	//订单类型01-带看订单,02-添加生成订单
	public static String ORDER_TYPE_LOOK ="01";
	public static String ORDER_TYPE_ADDSPAN ="02";
}
