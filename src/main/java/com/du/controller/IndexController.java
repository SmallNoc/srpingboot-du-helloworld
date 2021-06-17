package com.du.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

//在templates目录下所有页面资源，只有通Controller来进行访问
//需要模板引擎的支持 thymeleaf
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","hello spring boot");
        model.addAttribute("users", Arrays.asList("dyd","ydy","ddd"));
        return "index";
    }
}
