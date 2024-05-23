package com.example.greeting;

import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import reactor.core.publisher.Mono;

import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.client.WebSocketGraphQlClientInterceptor;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

public final class JwtGraphQlClientInterceptor implements WebSocketGraphQlClientInterceptor {

	private final String token;


	private JwtGraphQlClientInterceptor(String token) {
		this.token = token;
	}


	@Override
	public Mono<Object> connectionInitPayload() {
		return Mono.just(Map.of("Authorization", "Bearer " + this.token));
	}


	public static JwtGraphQlClientInterceptor create() {
		return new JwtGraphQlClientInterceptor(initToken());
	}

	private static String initToken() {
		ClassPathResource privResource = new ClassPathResource("simple.priv");
		ClassPathResource pubResource = new ClassPathResource("simple.pub");

		try (InputStream priv = privResource.getInputStream(); InputStream pub = pubResource.getInputStream()) {
			RSAPublicKey publicKey = RsaKeyConverters.x509().convert(pub);
			RSAPrivateKey privateKey = RsaKeyConverters.pkcs8().convert(priv);
			RSAKey key = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
			JwtEncoder encoder = new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(key)));
			JwtClaimsSet set = JwtClaimsSet.builder().subject("Markey").claim("scope", "greeting:read").build();
			return encoder.encode(JwtEncoderParameters.from(set)).getTokenValue();
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
