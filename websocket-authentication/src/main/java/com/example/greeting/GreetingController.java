package com.example.greeting;

import java.time.Duration;

import reactor.core.publisher.Flux;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

	@QueryMapping
	String greeting(Authentication authentication) {
		return "Hello " + authentication.getName() + "!";
	}

	@SubscriptionMapping
	Flux<String> greetings(Authentication authentication) {
		return Flux.interval(Duration.ofMillis(50))
			.map((num) -> "Hello " + authentication.getName() + num + "!");
	}

}
