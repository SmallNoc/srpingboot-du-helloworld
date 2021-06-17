package com.du.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/myWorld")
    //ResponseBody 返回字符串
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @GetMapping("/cool")
    @ResponseBody
    public String world(){
        return "新的开始";
    }
}
