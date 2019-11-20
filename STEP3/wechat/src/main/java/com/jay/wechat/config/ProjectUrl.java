package com.jay.wechat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projecturl")
@Component
public class ProjectUrl {

    ///微信公众账号授权url
    public String wechatMpAuthorize;

    //微信开放平台授权url（扫码登录
    private String wechatOpenAuthorize;

    //项目url
    private String sell;
}
