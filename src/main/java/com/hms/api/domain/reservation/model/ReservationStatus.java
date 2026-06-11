package com.hms.api.domain.reservation.model;

import com.hms.api.common.exception.BusinessException;
import lombok.Getter;

@Getter
public enum ReservationStatus {
  CREATED("Utworzona"),
  CONFIRMED("Potwierdzona"),
  CANCELLED("Anulowana"),
  CHECKED_IN("Zameldowany"),
  CHECKED_OUT("Wymeldowany"),
  NO_SHOW("Nie pojawił się");

  private final String label;

  ReservationStatus(String label) {
    this.label = label;
  }

  public String getCode() {
    return name().toLowerCase();
  }

  public static ReservationStatus fromCode(String code) {
    for (ReservationStatus status : values()) {
      if (status.name().equalsIgnoreCase(code)) {
        return status;
      }
    }
    throw new BusinessException("Nierozpoznany kod statusu rezerwacji: " + code);
  }
}
