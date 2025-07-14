package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 (싱글톤 패턴 관리)
public class WebMvcConfig implements WebMvcConfigurer {

    // DI 처리(생성자 의존 주입)
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // 인터셉터가 동작할 URI 패턴을 지정
               .addPathPatterns("/board/**", "/user/**", "/reply/**")
                // 인터셉터에서 제외할 URI 패턴 설정
               .excludePathPatterns("/board/{id:\\d+}");
                // \\d+ 는 정규표현식으로 1개 이상의 숫자를 의미
                // /board/1 , /board/22
    }

    // cors 정책 설정

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api-test/**")
                .allowedOrigins("*") // 모두 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 4가지 모두 허용
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(false); // 인증이 필요한 경우 true, 인증 필요없으면 false
                //.allowedOrigins("http://api.kakao.com:8080") 특정 도메인만 등록 가능
        // 필요시 중복 등록 가능
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600); // 1 hour to seconds
    }


}
