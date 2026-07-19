package com.hms.api.common.auth;

import com.hms.api.common.auth.dto.RegisterRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class AuthClient {

  private final RestClient.Builder builder;

  @Value("${hms.auth.url}")
  private String authUrl;

  public UUID register(RegisterRequest request) {
    return builder
        .build()
        .post()
        .uri(authUrl + "/auth/register")
        .body(request)
        .retrieve()
        .body(UUID.class);
  }

  public void inactivateUser(UUID userId) {
    builder
        .build()
        .put()
        .uri(authUrl + "/auth/{user-id}/inactivate", userId)
        .retrieve()
        .toBodilessEntity();
  }
}
