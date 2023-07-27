package com.bookjeok.bookdarak.config;

import com.bookjeok.bookdarak.dto.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePaths = new ArrayList<>();
        excludePaths.add("/login");
        excludePaths.add("/signup");
        excludePaths.add("/**"); // 임시로 모든 경로 허용

        registry.addInterceptor(userInterceptor())
                .excludePathPatterns(excludePaths);
    }

    @Bean
    public UserInterceptor userInterceptor(){
        return new UserInterceptor();
    }
}
