package com.trw.service.impl;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.trw.constant.Constant;
import com.trw.model.Sms;
import com.trw.util.ToolUtil;
import com.trw.vo.ResultMsg;


@Service
public class SmsService {
	
	@Autowired
	private RedisService redisService;

    @Value("${sms.accessId}")
    private String accessId;
    @Value("${sms.accessKey}")
    private String accessKey;
    @Value("${sms.signName}")
    private String signName;
    @Value("${sms.product}")
    private String product;     //短信API产品名称（短信产品名固定，无需修改）
    @Value("${sms.domain}")
    private String domain;      //dysmsapi.aliyuncs.com


    /**
     * 根据用户输入的phone发送验证码
     * @param phone 电话号码
     */
    public ResultMsg<String> sendSmsCode(String phone){
    	ResultMsg<String> msg = new ResultMsg<>();
    	String sendsms =redisService.getString(Constant.CONFIGPIX+"sendsms");
    	String codeMsg =redisService.getString(Constant.CONFIGPIX+"codeMsg");
    	if(!Constant.YES.equals(sendsms)){//不发送短信
    		msg.setCode(Constant.CODE_SUCC);
            msg.setMsg(sendsms);
            return msg;
    	}
        if(!ToolUtil.isPhone(phone)){
        	msg.setCode(Constant.CODE_FAIL);
        	msg.setMsg("手机号码格式不正确");
            return msg;
        }
       
        Sms sms = makeCode(phone);      //制作验证码，6位随机数字
        JSONObject smsJson=new JSONObject();
        smsJson.put("code",sms.getCode());
        SendSmsResponse sendSmsResponse=null;
        try {
            sendSmsResponse = send(phone,signName,codeMsg,smsJson);
        } catch (ClientException e) {
            e.printStackTrace();
            msg.setCode(Constant.CODE_FAIL);
            msg.setMsg(e.getMessage());
            return msg;
        }
        if("OK".equals(sendSmsResponse.getCode())) {
            //短信发送成功，将短信记录到redis中
            redisCode(sms);
            msg.setCode(Constant.CODE_SUCC);
            msg.setMsg("OK");
           
        }else {
        	 msg.setCode(Constant.CODE_FAIL);
             msg.setMsg(sendSmsResponse.getMessage());
        }
       
        return msg;
    }
    
    /**
     * 发送通知短信
     * @param phone 电话号码
     */
    public ResultMsg<String> sendNotic(String template,String phone,JSONObject smsJson){
    	ResultMsg<String> msg = new ResultMsg<>();
    	String sendsms =redisService.getString(Constant.CONFIGPIX+"sendsms");
    	if(!Constant.YES.equals(sendsms)){//不发送短信
    		msg.setCode(Constant.CODE_SUCC);
            msg.setMsg(sendsms);
            return msg;
    	}
        if(!ToolUtil.isPhone(phone)){
        	msg.setCode(Constant.CODE_FAIL);
        	msg.setMsg("手机号码格式不正确");
            return msg;
        }
        try {
        	send(phone,signName,template,smsJson);
        } catch (ClientException e) {
            e.printStackTrace();
            msg.setCode(Constant.CODE_FAIL);
            msg.setMsg(e.getMessage());
            return msg;
        } 
        msg.setCode(Constant.CODE_SUCC);
        msg.setMsg("OK");
        return msg;
    }
    
    //将验证码缓存到redis中，15分钟过后自动清除该缓存
    private void redisCode(Sms sms) {
    	redisService.set(sms.getPhone(),sms.getCode(),900L,TimeUnit.SECONDS);
    }

    //随机生成6位数的短信码
    private Sms makeCode(String phone) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for(int i=0;i<6;i++){
            int next =random.nextInt(10);
            code.append(next);
        }
        return new Sms(phone,code.toString(),System.currentTimeMillis());
    }

//    //判断验证功发送时候频繁
//    private boolean isSendOfen(String phone) {
//        if(redisService.get(phone)==null) {
//            return false;
//        }else{
//            //判断上一次记录的时间和当前时间进行对比，如果两次相隔时间小于120s，视为短信发送频繁
//            Sms sms=redisService.get(phone,Sms.class);
//            //两次发送短信中间至少有2分钟的间隔时间
//            if(sms.getTime()+120*1000>=System.currentTimeMillis()) {
//                return true;
//            }
//            return false;
//        }
//    }

    

    /**
     * 发送短信
     * @param phone
     * @param signName
     * @param templateCode
     * @param params
     * @return
     * @throws ClientException
     */
    SendSmsResponse send(String phone, String signName, String templateCode, JSONObject params) throws ClientException {
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessId,
                accessKey);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(params.toJSONString());
        request.setOutId(UUID.randomUUID().toString());
        //请求失败这里会抛ClientException异常
        return acsClient.getAcsResponse(request);
    }

}

