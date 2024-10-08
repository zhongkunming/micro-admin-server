package com.devkk.micro.config;

import com.devkk.micro.framework.interceptor.GlobalInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhongkunming
 */
@Configuration
public class WebServerConfig implements WebMvcConfigurer {

    @Resource
    private GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(globalInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(
//                        "/doc.html",
//                        "/favicon.ico",
//                        "/error",
//                        "/swagger-ui/index.html",
//                        "/swagger-ui*/**",
//                        "/webjars/**",
//                        "/v3/api-docs",
//                        "/v3/api-docs.yaml",
//                        "/v3/api-docs/swagger-config",
//                        "/share",
//                        "/login",
//                        "/logout"
//                );
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
