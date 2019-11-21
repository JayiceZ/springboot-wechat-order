package com.jay.wechat.handler;


import com.jay.wechat.config.ProjectUrl;
import com.jay.wechat.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrl projectUrl;

    //拦截登陆异常，要对这个异常进行拦截
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        //如果出现该异常，就跳转到扫码登陆的地方
        return new ModelAndView("redirect:".concat(projectUrl.getSell()).concat("/sell/wechat/qrAuthorize").concat("?returnUrl=").concat(projectUrl.getSell()).concat("/sell/seller/login"));
    }

}
