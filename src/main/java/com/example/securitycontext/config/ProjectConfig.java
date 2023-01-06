package com.example.securitycontext.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableAsync
@Configuration
public class ProjectConfig {

    @Bean
    public InitializingBean initializingBean(){
        return () -> SecurityContextHolder.setStrategyName(
                SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
        );
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var usd = new InMemoryUserDetailsManager();
        var user1 = User.withUsername("mary")
                .password("12345")
                .authorities("read")
                .build();
        usd.createUser(user1);
        return usd;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
