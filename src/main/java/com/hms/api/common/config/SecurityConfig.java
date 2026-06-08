package com.hms.api.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(corsConfigurationSource))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(HttpMethod.OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
