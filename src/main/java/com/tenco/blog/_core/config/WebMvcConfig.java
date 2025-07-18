package com.tenco.blog._core.config;

import com.tenco.blog._core.interceptor.IPAccessInterceptor;
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
	private final IPAccessInterceptor ipAccessInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(ipAccessInterceptor)
				.addPathPatterns("/**");

		registry.addInterceptor(loginInterceptor)
				// REST API 경로 변경
				.addPathPatterns("/api/**")
				// 공개 API는 제외 처리
				.excludePathPatterns(
						"/api/boards/{id:\\d+}/detail" // 게시글 상세보기 누구나 응답 허용
				);
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
