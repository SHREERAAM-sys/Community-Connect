package com.shree.communityconnect.config;

import com.shree.communityconnect.util.ThymeleafUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAsync
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/organizer/**", "/participant/**","/home")
                .excludePathPatterns("/", "/register"); // public paths here
    }

    @Bean
    public ThymeleafUtils thymeleafUtils() {
        return new ThymeleafUtils();
    }
}

