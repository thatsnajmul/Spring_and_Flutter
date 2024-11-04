package com.thatsnajmul.backend.security;

import com.thatsnajmul.backend.jwt.JwtAuthenticationFilter;
import com.thatsnajmul.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(

                                req ->
                                        req.requestMatchers("/login", "/register", "api/job/", "api/job-application/","/activate/**", "api/company/")
                                                .permitAll()
                                                .requestMatchers("api/job/save", "api/job-application/save")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers("api/job/{id}", "api/job-application/{id}","api/job/all/**")
                                                .hasAnyAuthority("ADMIN", "EDITOR")
                                                .requestMatchers("api/user/**")
                                                .hasAuthority("USER")

                        )
                        .userDetailsService(userService)
                        .sessionManagement(
                                session ->
                                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();


    }



    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



}
