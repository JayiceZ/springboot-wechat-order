package com.jay.wechat.Test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test1 {

    @RequestMapping("/jsp")
    public String test1(){
        return "create";
    }
}
