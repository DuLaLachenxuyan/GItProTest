package com.trw.util;

public class AlipayConfig {
		// 商户appid
		public static String APPID = "2018060760276830";
		// 私钥 pkcs8格式的
		public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4ycmOWmi7rI31\r\n" + 
				"fRvbfXO6FGP1cfN0zNmnCurRY9xPxcL0qr8st3eetbIVGSD9cJiZll6bLuLlYzfC\r\n" + 
				"yV95zIx2UNqrwaJDQgFEr0+Txomm54RbOt383Cgd6KZakDf8ldOm/kwdZtiIXU/Q\r\n" + 
				"DbIrGMegq/PTXaC31zmzGhVRVGV+ZP7IbHM3FpCOBbhHaCwtlac/W5oLOtyG6yqh\r\n" + 
				"0meQaLDrsumSwRA8Wd/fpd4JONBvPbmgSsLzFDvvuGcLU1wYljCcwTfGrX+ZBUrr\r\n" + 
				"TARDFt9S0/P3dNwFotBeNhFwcIozG1mHg0tmIsIiv4zAcnaoH+IB1ByHLn/xGkE1\r\n" + 
				"tQaffpCxAgMBAAECggEAagUUSEFk77Br7loufnr2x99lDnCnju7tPaLM4QAbagV7\r\n" + 
				"+mZwsxY8Ymm619IIFIDifNRCSWTYN2X6mx5wUfBi8osnOv4ou9AF7ENtM8roNUOF\r\n" + 
				"s9YNgUNLHQXbVACErr3Dpn8maoQUXgCzhxokjAd2mZ+ukCmayX+JklG3w2bZqG8O\r\n" + 
				"B6ShXT4zRIRfiuGpl5/D2QSKULLBYbgVCEuGVZpeGPNI6zzK0k3Ao7NUoOpKY6gq\r\n" + 
				"flrzEOEIUcxqK/oLYCPb1vclxgTA0QE+3mmm+ABvvEvfh/SkmfOegkqGJ6inu2DF\r\n" + 
				"m0Zn9q3c5yoLWre+In8rmxXueP3q+97LMnUkYrou5QKBgQDc3NqYv4UQtd29/Buu\r\n" + 
				"qlc7uKTlVBKYrPRGNxHez0uBawKeXZ4y7r9vhli7Xu1mk+OefFwwM/YoGPmjx9DN\r\n" + 
				"1NaLwWFaMcWH5c5PJzAijYSHff5Qn0o2d6QdqwASRj4Mr7H2HL/GbrOWTbv94pmt\r\n" + 
				"SZueAMvKrIQxRuglRTIPiP553wKBgQDWL7d0q3bjkx9E+tKsJBaTEEzzKqj9VZY/\r\n" + 
				"7NlB/NoxkfxrKYzloQrRSiAw/r0lAulRdeF6Wn1dkv9OUHp3DAcTvRPWtf9xnlOP\r\n" + 
				"EsoK/1EUiigYTI6kxkOmUPkrB5d++hWQdewp211dDJ/r4/JI6HOLLvG4FqvVH7x7\r\n" + 
				"LfRWq8FnbwKBgQCvAMyxBehREloOhOoGX3DZoHgAQrqtgTXZQR+G1eh1Nq6Pf4yv\r\n" + 
				"vN+xZdl/NjuT8REF7nI5u6KLqSZ9sQFxZvlNKcCS8CcdhGkR7PJGHq5pkdrmVSE/\r\n" + 
				"wVMoMuvOjxvxf3HamnMWr3bbXOLdzCSK221UKDtSb5obVZzykz6Ucq95LwKBgAkP\r\n" + 
				"M59HsVum2We9hDOrwxitU9PlNgihGh3SEs5rt37pQdf2hfMggKihjgMEg1ORb/T2\r\n" + 
				"G3dlZHAUy5+wX72xUItRMAmg4REZNUeT79/PNMq9nTyJ4G6vxCTAwAlF5O6F/WuC\r\n" + 
				"0V5j3OsWoZ0EozbaPijg73Bt5Q66rOo442DXGvMZAoGBAJTSz7y8MOUACB5PryWw\r\n" + 
				"hQ4m2DA/lWyrUk0zdaL8iMaKJyTMn8hHwxm+Y7xj/gidNFyL8GFUCxxgGpoVilYP\r\n" + 
				"dO2qqqu6NDbWTUi1RqnWEv7Wh9Il3xTo6rOlq6B8rFSi0WPDgWsC3HJj1xeC4aCx\r\n" + 
				"wXI75QP0U0LaLZO4f0Vub551";
		// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//		public static String notify_url = "http://5829273e.ngrok.io/trw/paysuccess";
		// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
//		public static String return_url = "http://5829273e.ngrok.io/trw/payfail";
		// 请求网关地址
		public static String URL = "https://openapi.alipay.com/gateway.do";
		// 编码
		public static String CHARSET = "UTF-8";
		// 返回格式
		public static String FORMAT = "json";
		// 支付宝公钥
		public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8pkXR9scaHWrxYaQ0X/vweXd3h6/yy9CtZKrUNAS3ujmJgmL3nb4h6e3nYt+An4jUd6nsG21xW24ztO2PrnDRv8n6dPy4A87vfo3FBxf0OQlUM+DbUmUZnxQCGj/c3J+u1mio2i+3dCvSMs7PhR35XfzivbP3+GixaI5CIqr2UJQRpkbce+N80Rdd3AOEfxOrUYWwZegNMY1q0voRekVO4iWNqjTvZ8sdqq3FYfUydvZ3dGFOWOO9x90Ods3/1OBWxXJn2fSxD7H6Qe6BE8zjEgtgziiVscDbryaftwMuCrwRYTfJWIpdTwu6b2GBrJ6LHrb5YlJiJjdgj5Ek+8v/wIDAQAB";
		// 日志记录目录
		public static String log_path = "/log";
		// RSA2
		public static String SIGNTYPE = "RSA2";
		
//		public static void main(String[] args) throws AlipayApiException {
//			String ss = "{gmt_create=2018-06-14 12:24:49, charset=UTF-8, seller_email=turongkj@126.com, subject=整存整取卅年, sign=ow4xAinauRCihqT+PCdi/DVzOwkHsMBeHSjrvGOtsLXO4h5mkmi2s7OZkJbYsdfbg7DwfiTEoEnf1NAbT/j7GgpuZNXlPKj2TloWah5zTANfnU1M8TAx8wDZShyWeMtbXsFFj1K/U5zEdugdgzclzzmzQsVMu61FGox3ByUCzHs2sB+yrJ1qrNzh3ix7H4rp5bgs3gHl2tmQP6DKOBSMRxjsNP8jB6Wxa+jpRZBrC+xYxtMnht4B99yV9hZOMhrTbJLeU0r3yICTbPEjvIzYKR6WbYM8hLsL7JurubUZMhn0Uoink+O0pytlK6ECLp4SLGj7GDcDCjojoBf+iDiIfQ==, body=整存整取卅年, buyer_id=2088702773990824, invoice_amount=0.01, notify_id=0d5a017ebfb2f828df84e83215c7b6embx, fund_bill_list=[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}], notify_type=trade_status_sync, trade_status=TRADE_SUCCESS, receipt_amount=0.01, buyer_pay_amount=0.01, app_id=2018060760276830, sign_type=RSA2, seller_id=2088131489313522, gmt_payment=2018-06-14 12:24:50, notify_time=2018-06-14 12:24:50, version=1.0, out_trade_no=004, total_amount=0.01, trade_no=2018061421001004820587354299, auth_app_id=2018060760276830, buyer_logon_id=305***@qq.com, point_amount=0.00}";
//			Map<String,String> params =new HashMap<>();
//			params.put("gmt_create", "2018-06-14 12:24:49");
//			params.put("charset", "UTF-8");
//			params.put("seller_email", "turongkj@126.com");
//			params.put("subject", "整存整取卅年");
//			params.put("sign", "ow4xAinauRCihqT+PCdi/DVzOwkHsMBeHSjrvGOtsLXO4h5mkmi2s7OZkJbYsdfbg7DwfiTEoEnf1NAbT/j7GgpuZNXlPKj2TloWah5zTANfnU1M8TAx8wDZShyWeMtbXsFFj1K/U5zEdugdgzclzzmzQsVMu61FGox3ByUCzHs2sB+yrJ1qrNzh3ix7H4rp5bgs3gHl2tmQP6DKOBSMRxjsNP8jB6Wxa+jpRZBrC+xYxtMnht4B99yV9hZOMhrTbJLeU0r3yICTbPEjvIzYKR6WbYM8hLsL7JurubUZMhn0Uoink+O0pytlK6ECLp4SLGj7GDcDCjojoBf+iDiIfQ==");
//			params.put("body", "整存整取卅年");
//			params.put("buyer_id", "2088702773990824");
//			params.put("invoice_amount", "0.01");
//			params.put("notify_id", "0d5a017ebfb2f828df84e83215c7b6embx");
//			params.put("fund_bill_list", "[{\"amount\":\"0.01\",\"fundChannel\":\"ALIPAYACCOUNT\"}]");
//			params.put("notify_type", "trade_status_sync");
//			params.put("trade_status", "TRADE_SUCCESS");
//			params.put("receipt_amount", "0.01");
//			params.put("buyer_pay_amount", "0.01");
//			params.put("app_id", "2018060760276830");
//			params.put("sign_type", "RSA2");
//			params.put("seller_id", "2088131489313522");
//			params.put("gmt_payment", "2018-06-14 12:24:50");
//			params.put("notify_time", "2018-06-14 12:24:50");
//			params.put("version", "1.0");
//			params.put("out_trade_no", "004");
//			params.put("total_amount", "0.01");
//			params.put("trade_no", "2018061421001004820587354299");
//			params.put("auth_app_id", "2018060760276830");
//			params.put("buyer_logon_id", "305***@qq.com");
//			params.put("point_amount", "0.00");
//			boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
//			System.out.println(verify_result);
//		}
}
