package com.diplom_proj.shop.config;

import com.diplom_proj.shop.services.CustomUserDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private CustomUserDetailServices customUserDetailServices;
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomUserDetailServices customUserDetailServices, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailServices = customUserDetailServices;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    private final String[] publicUrl = {"/",
            "/css/**",
            "/*.css",
            "/webjars/**",
            "/register",
            "/register/**",
            "/error"
    };

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http.authorizeHttpRequests(auth -> {
            // Разрешаем доступ всем (permitAll) к главной странице и другим публичным URL
            auth.requestMatchers(publicUrl).permitAll();

            // Все остальные страницы будут требовать авторизации
            auth.anyRequest().authenticated();
        });

        // ... остальные настройки (formLogin и т.д.)

        return http.build();
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailServices);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();// Раскомитить если нужно включить хеширование пароля
        return NoOpPasswordEncoder.getInstance();
    }
}
