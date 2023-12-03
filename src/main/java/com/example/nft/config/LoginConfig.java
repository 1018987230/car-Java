package com.example.nft.config;

import com.example.nft.Handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 先关闭
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                "/api/operator/login",
                "/api/wx/**",
                "/api/admin/register",
                "/api/consumer/wx/**",
                "/api/consumer/wx/getToken/**",
                "/api/store/**",  // 暂时这样，后面新建主端后取消
                "/api/operator/add",
                "/api/consumer/login",
                "/api/car/recognize"
        );
    }
}
