package com.group2.theminimart.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.group2.theminimart.entity.User;
import com.group2.theminimart.exception.UserNotFoundException;
import com.group2.theminimart.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

  @Value("${env.data.secretkey:secret-key}")
  private String secretKey;

  private final UserRepository userRepository;

  @PostConstruct
  protected void init() {
    // this is to avoid having the raw secret key available in the JVM
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String username, List<String> roles) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + 3600000); // 1 hour
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    return JWT.create()
        .withSubject(username)
        .withClaim("roles", 
            userRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException())
            .getRoles()
            .stream()
            .map(role -> "ROLE_" + role)
            .toList())
        .withIssuedAt(now)
        .withExpiresAt(validity)
        .sign(algorithm);
  }

  public Authentication validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    JWTVerifier verifier = JWT.require(algorithm)
        .build();

    DecodedJWT decoded = verifier.verify(token);

    User user = userRepository.findByUsername(decoded.getSubject()).orElseThrow(() -> new UserNotFoundException());

    List<String> roles = decoded.getClaim("roles").asList(String.class);
    List<SimpleGrantedAuthority> authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .toList();

    return new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
  }

}
