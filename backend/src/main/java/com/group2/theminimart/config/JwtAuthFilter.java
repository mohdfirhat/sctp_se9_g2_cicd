package com.group2.theminimart.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserAuthenticationProvider userAuthenticationProvider;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null) {
      String[] authElements = header.split(" ");

      if (authElements.length == 2
          && "Bearer".equals(authElements[0])) {
        try {
          SecurityContextHolder.getContext().setAuthentication(
              userAuthenticationProvider.validateToken(authElements[1]));
        } catch (RuntimeException e) {
          SecurityContextHolder.clearContext();
          throw e;
        }
      }
    }

    filterChain.doFilter(request, response);
  }

}
