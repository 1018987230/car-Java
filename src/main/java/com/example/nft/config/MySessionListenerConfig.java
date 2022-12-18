package com.example.nft.config;

import com.example.nft.Handler.MySessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MySessionListenerConfig implements WebMvcConfigurer {

    @Bean
    public ServletListenerRegistrationBean getListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new MySessionListener());
        return servletListenerRegistrationBean;
    }
}


