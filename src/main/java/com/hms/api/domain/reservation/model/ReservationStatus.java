package com.hms.api.domain.reservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import com.hms.api.common.jackson.CodeNameEnum;
import com.hms.api.common.jackson.CodeNameResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(implementation = CodeNameResponse.class)
public enum ReservationStatus implements CodeNameEnum {
  CREATED("Utworzona"),
  CONFIRMED("Potwierdzona"),
  CANCELLED("Anulowana"),
  CHECKED_IN("Zameldowany"),
  CHECKED_OUT("Wymeldowany"),
  NO_SHOW("Nie pojawił się");

  private final String name;

  ReservationStatus(String name) {
    this.name = name;
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
