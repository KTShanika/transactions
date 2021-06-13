package com.daofab.transactions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DaoFabWebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private DaoFabInterceptor daoFabInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(daoFabInterceptor);
    }
}
