package com.axing.reggie.exception;

import com.axing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理,相当于笔记的ProjectExceptionAdvice
 */
@Slf4j
@RestControllerAdvice
@ResponseBody//需要响应回json
public class GlobalExceptionHandler {
    /**
     * 异常处理方法，处理用户名重复问题
     * @param ex
     * @return
     */
    // @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    // public R<String> doUsernameException(SQLIntegrityConstraintViolationException ex){
    //     log.info("异常信息1:{}",ex.getMessage());
    //     if (ex.getMessage().contains("Duplicate entry")){
    //         String[] s = ex.getMessage().split(" ");
    //         String msg = "名称： "+s[2]+" 已存在";
    //         return R.error(msg);
    //     }
    //     return R.error("未知错误");
    // }
    /*
    备注：没搞懂为什么 ‘SQLIntegrityConstraintViolationException’异常 和 ’Exception‘异常一起出现
         时，不是就近原则执行SQLIntegrityConstraintViolationException拦截的方法,想使用上面方法需要
         注释掉 ‘@ExceptionHandler(Exception.class)’
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public R<String> doUsernameException(DuplicateKeyException ex){
        log.info("异常信息2:{}",ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] s = ex.getMessage().split(" ");
            String msg = "名称： "+s[9]+" 已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    /**
     * 处理自定义业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R<String> doBusinessException(BusinessException ex){
        return R.error(ex.getMessage());
    }












    /**
     * 处理所有异常
     * @param ex
     * @return
     */
    // @ExceptionHandler(Exception.class)
    public R<String> doException(Exception ex){
        log.info("进入未处理异常！！异常信息:{}",ex.getMessage());
        //记录日志
        //发送邮件给开发人员
        //发送短信给运维人员
        return R.error("未知的系统错误");
    }


}
