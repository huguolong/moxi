package com.moxi.exceptions;

/**
 * 业务逻辑错误异常
 * @author zhouzq
 * @date 2018-04-03
 */
public class BusinessException extends RuntimeException {

    private int errCode;

    private String errDetail;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int errCode) {
        this.errCode = errCode;
    }

    public BusinessException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(int errCode, String message, String errDetail) {
        super(message);
        this.errCode = errCode;
        this.errDetail = errDetail;
    }

    public BusinessException(int errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public BusinessException(int errCode, Throwable cause) {
        super(cause);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrDetail() {
        return errDetail;
    }
}
