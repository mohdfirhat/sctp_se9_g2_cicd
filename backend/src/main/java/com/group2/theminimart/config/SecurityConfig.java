package com.group2.theminimart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
  private final UserAuthenticationProvider userAuthenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .exceptionHandling(handling -> handling.authenticationEntryPoint(userAuthenticationEntryPoint))
        .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
        .csrf(csrf -> csrf.disable())
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests((requests) -> requests
            // .requestMatchers("/api/users/**").authenticated()
            // .requestMatchers("/api/users/**", "/api/cart/**").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.POST, "/api/products/").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.PUT, "/api/products/").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.DELETE, "/api/products/").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.POST, "/api/ratings/").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.PUT, "/api/ratings/").hasRole("ADMIN")
            // .requestMatchers(HttpMethod.DELETE, "/api/ratings/").hasRole("ADMIN")
            // .anyRequest().permitAll());

            // only admin can get list of all users
            .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
            // authenticated users can update own passwords
            .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated()
            // authenticated users can delete own account
            .requestMatchers(HttpMethod.DELETE, "/api/users").authenticated()
            // authenticated users can CRUD their own ratings
            .requestMatchers(HttpMethod.POST, "/api/users/ratings/products/**").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/users/ratings").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/users/ratings/products/**").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/users/ratings/products/**").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/users/ratings/products/**").authenticated()
            // authenticated users can CRUD their own cart
            .requestMatchers("/api/users/cart/**").authenticated()
            // all other endpoints allowed
            .anyRequest().permitAll()); 
    return http.build();
  }
}
