package com.jay.wechat.aspect;


import com.jay.wechat.constant.CookieConstant;
import com.jay.wechat.constant.RedisConstant;
import com.jay.wechat.exception.SellerAuthorizeException;
import com.jay.wechat.utils.CookieUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@AllArgsConstructor
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //切入点为com.jay.wechat.controller下的以Seller开头的接口中的所有方法,除了SellerInfoController中的，因为它就是处理登录注销的逻辑
    @Pointcut("execution(public * com.jay.wechat.controller.Seller*.*(..)) && !execution(public * com.jay.wechat.controller.SellerInfoController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        System.out.println("before");

        //获取HttpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie==null){
            System.out.println("切面");
            throw new SellerAuthorizeException();
        }

        //去redis中查
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX+cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            System.out.println("切面");
            throw new SellerAuthorizeException();
        }


    }

}
