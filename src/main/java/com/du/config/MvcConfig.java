package com.du.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

//如果想要把自己的一些diy功能 只需要将自定以组件写在MvcConfig中，并通过@Bean注册到mvc中，就会交给SpringBoot托管 自动装配
//扩展 spingMvc  dispatchservlet
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //ViewResolver 视图解析器  实现ViewResolver的类就可以叫做视图解析器
    //通过@Bean注解中 当@Configuration
    @Bean
    public ViewResolver myViewResolver(){
        return new myViewResolver();
    }

    //自己定义一个组件（这里举例了一个自定义了视图解析器myViewResolver）
    public static class myViewResolver implements ViewResolver{
        @Override
        public View resolveViewName(String s, Locale locale) throws Exception {
            return null;
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/dyd").setViewName("index");
    }
}

