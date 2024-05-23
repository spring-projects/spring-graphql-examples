package com.example.greeting;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebSocketGraphQlInterceptor;
import org.springframework.graphql.server.support.BearerTokenAuthenticationExtractor;
import org.springframework.graphql.server.webmvc.AuthenticationWebSocketInterceptor;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
public class WebMvcSecurityConfig {

	@Bean
	SecurityFilterChain filters(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll());
		return http.build();
	}

	@Bean
	public WebSocketGraphQlInterceptor authenticationInterceptor(@Value("classpath:simple.pub") RSAPublicKey pub) {
		return new AuthenticationWebSocketInterceptor(
				new BearerTokenAuthenticationExtractor(),
				new ProviderManager(new JwtAuthenticationProvider(NimbusJwtDecoder.withPublicKey(pub).build())));
	}
}
