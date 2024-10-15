package com.demo.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserStoreConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager(); // By default, the strategy retrieves users from memory, but we can configure this Bean to fetch user details from a database
        userDetailsManager.createUser(User.withUsername("user").password("{noop}password").roles("USER").build());
        return userDetailsManager;
    }
}
