package com.hms.api.common.jwt;

import com.hms.api.common.exception.BusinessException;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final String USER_ID_CLAIM = "user_id";

  public UUID requireAppUserId(Jwt jwt) {
    UUID userId = getAppUserId(jwt);
    if (userId == null) {
      throw new BusinessException("Brak user_id w tokenie", HttpStatus.UNAUTHORIZED);
    }
    return userId;
  }

  private UUID getAppUserId(Jwt jwt) {
    String userId = jwt.getClaimAsString(USER_ID_CLAIM);
    if (userId == null) {
      return null;
    }
    return UUID.fromString(userId);
  }
}
