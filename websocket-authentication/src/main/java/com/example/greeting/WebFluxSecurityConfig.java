package com.example.greeting;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebSocketGraphQlInterceptor;
import org.springframework.graphql.server.support.BearerTokenAuthenticationExtractor;
import org.springframework.graphql.server.webflux.AuthenticationWebSocketInterceptor;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@ConditionalOnMissingClass("org.springframework.web.servlet.DispatcherServlet")
public class WebFluxSecurityConfig {

	@Bean
	SecurityWebFilterChain webFilters(ServerHttpSecurity http) {
		http.authorizeExchange((authorize) -> authorize.anyExchange().permitAll());
		return http.build();
	}

	@Bean
	public WebSocketGraphQlInterceptor authenticationInterceptor(@Value("classpath:simple.pub") RSAPublicKey pub) {
		return new AuthenticationWebSocketInterceptor(
				new BearerTokenAuthenticationExtractor(),
				new JwtReactiveAuthenticationManager(NimbusReactiveJwtDecoder.withPublicKey(pub).build()));
	}

}
