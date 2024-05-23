# Overview

GraphQL over WebSocket with an authentication token passed through the `"connection_init"` message.

# Configuration

The `WebMvcSecurityConfig` and `WebFluxSecurityConfig` configure the `AuthenticationWebSocketInterceptor`
required to perform the authentication. 

To switch between WebMvc or WebFlux as the transport, comment in and out
`spring-boot-starter-web` and `spring-boot-starter-websocket` in build.gradle.

# Running

1. Run `GreetingApplication` from your IDE, or `./gradlew bootRun` from the command line to start the server.
2. Run `GreetingClient`, or `./gradlew clientRun` to execute a subscription.

Or you can run the integration tests in `GreetingApplicationTests`.
