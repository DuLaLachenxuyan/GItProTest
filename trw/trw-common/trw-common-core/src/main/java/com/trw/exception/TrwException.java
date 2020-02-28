package com.trw.exception;

import com.trw.enums.ServiceExceptionEnum;

/**
 * 封装trw的异常
 *
 */
public class TrwException extends RuntimeException {

	private static final long serialVersionUID = 2271366346996591539L;

	private Integer code;

    private String message;

    public TrwException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

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
