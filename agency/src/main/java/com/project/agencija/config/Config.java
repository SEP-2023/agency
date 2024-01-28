package com.project.agencija.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class Config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("https://localhost:" + env.getProperty("frontend.port"))
                //.allowedOrigins("http://localhost:" + env.getProperty("frontend.port"))
                .allowedOrigins("*")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE")
                .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization");}
}
