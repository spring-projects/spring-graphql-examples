package com.example.greeting;

import java.net.URI;

import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

public class GreetingClient {

	public static void main(String[] args) {

		WebSocketGraphQlClient graphQlClient = WebSocketGraphQlClient
				.builder(URI.create("ws://localhost:8080/graphql"), new ReactorNettyWebSocketClient())
				.interceptor(JwtGraphQlClientInterceptor.create())
				.build();

		graphQlClient.document("subscription {greetings}")
				.retrieveSubscription("greetings")
				.toEntity(String.class)
				.take(5)
				.doOnNext(System.out::println)
				.blockLast();
	}

}
