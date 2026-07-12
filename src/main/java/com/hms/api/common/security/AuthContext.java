package com.hms.api.common.security;

import com.hms.api.common.exception.BusinessException;
import com.hms.api.domain.reservation.model.ReservationSource;
import java.util.Objects;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {

  private static final String USER_ID_CLAIM = "user_id";

  public boolean isAdmin() {
    return hasRole("ADMIN");
  }

  public boolean isEmployee() {
    return isAdmin() || hasRole("EMPLOYEE");
  }

  public boolean isGuest() {
    return isAdmin() || hasRole("GUEST");
  }

  public boolean hasRole(String role) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new AuthenticationCredentialsNotFoundException("Authentication required");
    }
    String authority = "ROLE_" + role.toUpperCase();
    return authentication.getAuthorities().stream()
        .anyMatch(granted -> Objects.requireNonNull(granted.getAuthority()).equals(authority));
  }

  public UUID currentUserId() {
    String userId = currentJwt().getClaimAsString(USER_ID_CLAIM);
    if (userId == null) {
      throw new BusinessException("Brak user_id w tokenie", HttpStatus.UNAUTHORIZED);
    }
    return UUID.fromString(userId);
  }

  public ReservationSource requestSource() {
    String clientId = currentJwt().getClaimAsString("client_id");
    if (clientId == null) {
      return ReservationSource.OTHER;
    }
    return ReservationSource.fromCode(clientId);
  }

  public Jwt currentJwt() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
      throw new BusinessException("Brak kontekstu uwierzytelnienia", HttpStatus.UNAUTHORIZED);
    }
    return jwt;
  }
}
