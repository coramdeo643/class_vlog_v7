package com.tenco.blog._core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger Basic Setting Class
 * API Docs, Title, Description, JWT Token Setting
 */
@Configuration
public class OpenApiConfig {

	@Bean // IoC 대상
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				// API 기본 정보 추가 가능
				.info(new Info()
						.title("Tenco Blog API")
						.description("RESTful API ver 1.0")
						.version("1.0"))
				.components(new Components()
						.addSecuritySchemes("JWT",
								new SecurityScheme()
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT")))
				// 모든 API에 JWT 인증 적용
				.addSecurityItem(new SecurityRequirement().addList("JWT"));
	}
}
