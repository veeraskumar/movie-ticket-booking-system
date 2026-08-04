package com.rvk.mtbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI openAPI() {

		String scheme = "bearerAuth";

		return new OpenAPI()
				.info(new Info().title("Movie Ticket Booking System API").version("1.0")
						.description("Spring Boot + JWT Authentication"))
				.addSecurityItem(new SecurityRequirement().addList(scheme))
				.schemaRequirement(scheme, new SecurityScheme().name(scheme).type(SecurityScheme.Type.HTTP)
						.scheme("bearer").bearerFormat("JWT"));
	}
}