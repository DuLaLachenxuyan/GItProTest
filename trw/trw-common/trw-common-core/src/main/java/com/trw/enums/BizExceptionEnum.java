package com.trw.enums;


public enum BizExceptionEnum implements ServiceExceptionEnum {
	/**
	 * 字典
	 */
	DICT_EXISTED(400,"字典已经存在"),
	ERROR_CREATE_DICT(500,"创建字典失败"),
	ERROR_WRAPPER_FIELD(500,"包装字典属性失败"),
	DICT_MUST_BE_NUMBER(400,"字典的值必须为数字"),
	/**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),
    TOKEN_NULL(700, "token为空"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误");

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
