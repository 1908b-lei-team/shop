package com.fh.config;

import com.fh.common.Idempotent;
import com.fh.interceptor.IdempotentInterceptor;
import com.fh.interceptor.TokenInterceptor;
import com.fh.resolver.MemberResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class appConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private MemberResolver memberResolver;
    @Autowired
    private IdempotentInterceptor idempotentInterceptor;

    @Bean
    public MemberResolver memberResolver(){
        return new MemberResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns
                ("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberResolver);
    }
}
