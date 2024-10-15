package com.demo.client_server.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ClientController {

    private final WebClient webClient;

    public ClientController(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:9090").build();
    }

    @GetMapping("home")
    public Mono<String> home(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient,
                             @AuthenticationPrincipal OidcUser oidcUser) {
        return Mono.just("""
                <h4> Access Token: %s </h4>
                <h4> Refresh Token: %s </h4>
                <h4> Id Token: %s </h4>
                <h4> Claims: %s </h4>
                """.formatted(
                oAuth2AuthorizedClient.getAccessToken().getTokenValue(),
                oAuth2AuthorizedClient.getRefreshToken().getTokenValue(),
                oidcUser.getIdToken().getTokenValue(),
                oidcUser.getClaims()));
    }

    @GetMapping("tasks")
    public Mono<String> getTasks(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        return webClient
                .get()
                .uri("tasks")
                .header("Authorization", "Bearer %s".formatted(oAuth2AuthorizedClient.getAccessToken().getTokenValue()))
                .retrieve()
                .bodyToMono(String.class);
    }
}
