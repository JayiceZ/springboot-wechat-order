package com.jay.wechat.controller;

import com.jay.wechat.enums.ResultEnum;
import com.jay.wechat.exception.SellException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

/*
* 手机打开项目要访问两个接口
* 1.发现没有携带openid来访问项目，不合法，向后端发送抓取openid的请求
* 2.访问sell/wechat/authorize来获取访问者的openid（微信号相关）,或者更多信息，如头像，昵称
* 3.把获得到的openid拼接到项目路径url中，再去访问，这次合法了
* 4.访问sell/buyer/product/list展示商品
* 5.openid会被前端放在cookie之中
* */
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    //https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
    //手动方法(不使用)
    //用户同意授权后，将会进入该方法中，也就是访问 REDIRECT_URI?code=CODE&state=STATE，code信息用来获取用户微信号，昵称，头像等信息
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        //通过code获取url
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        //获得json字符串
        String respones=restTemplate.getForObject(url,String.class);
        /*
        * {
            "access_token":"ACCESS_TOKEN",
            "expires_in":7200,
            "refresh_token":"REFRESH_TOKEN",
            "openid":"OPENID",
            "scope":"SCOPE"
            }
        * */
    }

    //第三方sdk方法
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        System.out.println("进入authorize");
        //配置
        //调用方法
        String url="http://jay.natapp1.cc/sell/wechat/userInfo";
            //访问获取code
        String redirect = wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        return "redirect:"+redirect;
    }

    @GetMapping("/userInfo")
    //接收到code,用来获取access_token
    public String userInfo(@RequestParam("code") String code,@RequestParam("state") String returnUrl){
        System.out.println("code:"+code);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e){
            e.printStackTrace();
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        //获得了openid
        String openID=wxMpOAuth2AccessToken.getOpenId();
        System.out.println("token:"+wxMpOAuth2AccessToken.getAccessToken());
        //将openid返回
        return "redirect:"+returnUrl+"?openid="+openID;
        //return "redirect:http://www.baidu.com?openid="+openID;
    }
}
