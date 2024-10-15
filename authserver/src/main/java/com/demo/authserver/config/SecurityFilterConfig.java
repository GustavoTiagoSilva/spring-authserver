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
        1. A arquitetura do Spring Security foi desenhada seguindo a ideia de "Filtros". Filtros interceptam
        as requisições enviadas pelo cliente e executam validações customizadas configuradas pelo desenvolvedor da
        aplicação.

        2. A Classe SecurityConfig é uma classe que foi criada para centralizar todas as configurações de segurança
        que o AuthorizationServer precisa seguir
     */

    @Bean
    @Order(1)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // 1. Ativando as configurações básicas do OAuth para serem suportadas pelo servidor de autorização
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // 2. Habilitar o OpenID para termos o fluxo de autenticação contemplado
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()); // 2.1 Habilitando o OpenID Connect com seus valores padrão

        // 3. Caso o usuário não esteja autenticado, irá ser direcionado por padrão para a rota /login (OIDC).oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        http.exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")));

        // 4. Informando o tipo de token
        http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt(Customizer.withDefaults());
        });

        return http.build();
    }

    /*
    * O Bean definido acima diz respeito as configurações específicas do OAuth.
    * Uma delas é justamente redirecionar para a rota de login caso a requisicção não esteja autenticada.
    * Porém, ali informamos apenas o que precisa ocorrer caso uma excecção ocorra, mas precisamos agora definir esse tipo de exceção.
    * Por isso, criamos o Bean padrão abaixo. Ele centraliza todas as configuraçoões padrão
    *
    * */

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Qualquer requisição deve ser autenticada
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }

}
