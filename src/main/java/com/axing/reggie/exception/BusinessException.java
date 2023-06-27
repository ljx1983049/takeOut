package com.axing.reggie.exception;

/**
 * 自定义项目业务异常
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }

}
