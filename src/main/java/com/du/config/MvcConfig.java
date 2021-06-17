package com.du.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//扩展 spingMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //ViewResolver 视图解析器  实现ViewResolver的类就可以叫做视图解析器

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }


}

