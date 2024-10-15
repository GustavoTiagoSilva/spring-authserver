package com.demo.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

/* Class responsible for configuring the registration of clients authorized to access the application's resources.*/
@Configuration
public class ClientStoreConfig {

    // The Bean below is responsible for defining how clients will be registered. We can use different strategies for this registration. In this example, we will use an in-memory storage strategy.
    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        var registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-server")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/login/oauth2/code/client-server-oidc") // Specifying the client application's URI that will receive the generated Code, which is necessary to request the access token.
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(
                        ClientSettings
                                .builder()
                                .requireAuthorizationConsent(true)
                                .build()
                )
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

}
