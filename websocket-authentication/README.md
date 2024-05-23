# Overview

GraphQL over WebSocket with an authentication token passed through the `"connection_init"` message.

# Configuration

[WebMvcSecurityConfig](src/main/java/com/example/greeting/WebMvcSecurityConfig.java) and 
[WebFluxSecurityConfig](src/main/java/com/example/greeting/WebFluxSecurityConfig.java) configure the
`AuthenticationWebSocketInterceptor` to perform authentication. See the
[reference docs](https://docs.spring.io/spring-graphql/reference/transports.html#server.interception.websocket).

To switch between WebMvc or WebFlux as the transport, comment in and out
`spring-boot-starter-web` and `spring-boot-starter-websocket` in build.gradle.

# Running

1. Run [GreetingApplication](src/main/java/com/example/greeting/GreetingApplication.java) from your IDE, or `./gradlew bootRun` from the command line to start the server.
2. Run [GreetingClient](src/main/java/com/example/greeting/GreetingClient.java), or `./gradlew clientRun` to perform a subscription.

Or you can run the integration tests in [GreetingApplicationTests](src/test/java/com/example/greeting/GreetingApplicationTests.java).
