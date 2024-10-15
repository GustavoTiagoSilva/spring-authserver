package com.demo.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityFilterConfig {

    /*
        1. The architecture of Spring Security is built around the concept of "Filters." These filters intercept client
        requests and execute custom validations configured by the application developer.

        2. The `SecurityConfig` class was created to centralize all the security configurations required by the
        Authorization Server.

     */

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // 1. Enabling the basic OAuth configurations to be supported by the authorization server.
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // 2. Enabling OpenID to include the authentication flow.
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()); // 2.1 Habilitando o OpenID Connect com seus valores padrão

        // 3. If the user is not authenticated, they will be automatically redirected to the `/login` route (OIDC).
        http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        // 4. Specifying the token type.
        http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(Customizer.withDefaults());
        });

        return http.build();
    }

    /*
        The Bean defined above handles the specific configurations for OAuth.
        One of these configurations is redirecting to the login route if the request is unauthenticated.
        However, the previous Bean only specifies what should happen if an exception occurs,
        but we still need to define the type of exception.
        That's why we create the default Bean below, which centralizes all types of standard configurations.
    */

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Every request must be authenticated.
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

}
