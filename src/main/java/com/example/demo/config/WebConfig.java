package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저에서 /files/** 로 시작하는 주소로 요청이 오면
        registry.addResourceHandler("/files/**")
                // 실제 내 컴퓨터의 이 폴더에서 파일을 찾아라! (끝에 반드시 / 를 붙여야 함)
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/src/main/resources/static/files/");
    }
}