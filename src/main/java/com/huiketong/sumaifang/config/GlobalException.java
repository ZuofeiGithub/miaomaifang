package com.huiketong.sumaifang.config;

import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.ExceptionResp;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获
 * 新建一个Class,这里取名GlobalDefaultExceptionHandler
 * 在Class上添加注解,@ControllerAdvice;
 * 在class中添加一个方法
 * 在方法上添加@ExceptionHandler拦截相应的异常信息
 * 如果返回的是View 方法的返回值是ModelAndView;
 * 如果返回的是String或是Json数据，那么需要在方法上添加@RequestBody注解
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionResp defaultExceptionHandler(Exception e) {
        ExceptionResp resp = new ExceptionResp();
        e.printStackTrace();
        resp.setCode(500).setMsg(e.toString());
       return resp;
        //return "对不起，服务器繁忙，请稍后再试!"+e.getMessage();
    }
}
