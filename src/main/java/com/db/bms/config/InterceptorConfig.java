package com.db.bms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    private static final String[] all = {
            "/**/*.html",
            "/bookInfo/**",
            "/bookType/**",
            "/borrow/**",
            "/update/**",
            "/user/**"
    };


    public static final String[] aboutLogin = {
            "/",
            "/index.html",
            "/register.html",
            "/user/login",
            "/user/register"
    };


    public static final String[] aboutReader = {
            "/reader/**/*.html",    // 读者页面
            "/*/reader/**",         // 读者请求
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
