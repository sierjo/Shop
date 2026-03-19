package com.diplom_proj.shop.config;

import com.diplom_proj.shop.services.CustomUserDetailServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
            "/photos/**",
            "/icons/**",
            "/*.css",
            "/webjars/**",
            "/register",
            "/products/**",
            "/register/**",
            "/login/**",
            "/error",
            "/strona"
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

        //Configure custom login page and success handler
        http.formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .successHandler(customAuthenticationSuccessHandler))
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/strona");
                }).cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

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
