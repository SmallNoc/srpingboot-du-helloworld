package com.du.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("user/login")
//    @ResponseBody
    public String login(@RequestParam("username") String userName , @RequestParam("password") String  password, Model model, HttpSession session){
        if(!StringUtils.containsWhitespace(userName) &&"1".equals(password)){
            //return "redirect:/main.html";  重定向
            session.setAttribute("loginUser",userName);
            return "redirect:/main.html";
        }else{
            model.addAttribute("msg","用户名密码错误");
            return "index";
        }

    }
}
