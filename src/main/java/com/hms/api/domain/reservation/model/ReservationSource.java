package com.hms.api.domain.reservation.model;

import com.hms.api.common.exception.BusinessException;
import lombok.Getter;

@Getter
public enum ReservationSource {
  HMS_WEB("Aplikacja HMS Web"),
  HMS_MOB("Aplikacja HMS Mobile"),
  PHONE("Telefonicznie"),
  EMAIL("Emailowo"),
  RECEPTION("Przez recepcję"),
  OTHER("Inne");

  private final String label;

  ReservationSource(String label) {
    this.label = label;
  }

  public String getCode() {
    return name().toLowerCase();
  }

  public static ReservationSource fromCode(String code) {
    for (ReservationSource source : values()) {
      if (source.getCode().equalsIgnoreCase(code)) {
        return source;
      }
    }
    throw new BusinessException("Nieznane źródło rezerwacji: " + code);
  }
}
