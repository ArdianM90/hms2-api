package com.hms.api.domain.reservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import com.hms.api.common.jackson.CodeLabelEnum;
import com.hms.api.common.jackson.CodeLabelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(implementation = CodeLabelResponse.class)
public enum ReservationStatus implements CodeLabelEnum {
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

  @JsonCreator
  public static ReservationStatus fromCode(String code) {
    for (ReservationStatus status : values()) {
      if (status.name().equalsIgnoreCase(code)) {
        return status;
      }
    }
    throw new BusinessException("Nierozpoznany kod statusu rezerwacji: " + code);
  }
}
