package com.jay.wechat.controller;

import com.jay.wechat.bean.SellerInfo;
import com.jay.wechat.config.ProjectUrl;
import com.jay.wechat.constant.CookieConstant;
import com.jay.wechat.constant.RedisConstant;
import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.service.SellerInfoService;
import com.jay.wechat.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//后台管理系统的登录

@Controller
@RequestMapping("/seller")
public class SellerInfoController {
    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    //需要往redis中存入string类型的值
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrl projectUrl;


    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String  openid, HttpServletResponse httpServletResponse){
        Map<String,Object> map=new HashMap<>();
        //1.openid要与数据库中的数据匹配
        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
        if (sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url","/sell/seller/order/list");
            System.out.println("error");
            return new ModelAndView("/common/error",map);
        }

        /*
        * 这么做的原因是，下次要进行访问的时候，去cookie里寻找到token里的token值，然后在redis中找到对应的openid，看openid是否符合要求
        * */

        //2.设置token至redis
        String token= UUID.randomUUID().toString();
        System.out.println(token);
        Integer expire = RedisConstant.EXPIRE;
        //以token_+token为key,openid为value，expire为过期时间，单位为秒
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX+token),openid,expire, TimeUnit.SECONDS);

        //3.设置token至cookie
        CookieUtil.set(httpServletResponse, CookieConstant.TOKEN,token,expire);

        return new ModelAndView("redirect:"+ projectUrl.getSell()+"/sell/seller/order/list");
    }

    //注销
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> map=new HashMap<>();
        //从cookie中查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null){
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX+cookie.getValue()));
            //清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success",map);
    }
}
