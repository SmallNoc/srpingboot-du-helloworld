package com.du.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocalResolver implements LocaleResolver {

    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //获取请求中语言参数
       String language =  httpServletRequest.getParameter("l");
       //获取默认
       Locale locale = Locale.getDefault();
       //如果请求连接携带了国际化参数
//       if(!StringUtils.hasLength(language)){
//           System.out.println(language);
//          String[] strings=  language.split("_");
//           locale =  new Locale(strings[0],strings[1]);
//       }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
