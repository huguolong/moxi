package com.moxi.exceptions;

/**
 * @author zhouzq
 * @date 2020/3/18
 * @desc 框架异常
 */
public class FrameworkException extends BaseUnCheckedException {

    private static final long serialVersionUID = -8074816977312177577L;

    public FrameworkException(String msg) {
        super(msg);
    }

    public FrameworkException(String msg, Throwable ex) {
        super(msg, ex);
    }

}
