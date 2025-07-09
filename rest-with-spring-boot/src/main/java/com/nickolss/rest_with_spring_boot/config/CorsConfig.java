package com.nickolss.rest_with_spring_boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Value("${cors.originPatterns:http://localhost:8080}")
    private String corsOriginPatterns;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**")  // Todas as requisições precisam de CORS
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowCredentials(true);
    }

}
