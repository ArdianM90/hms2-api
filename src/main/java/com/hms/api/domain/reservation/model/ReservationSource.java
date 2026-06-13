package com.hms.api.domain.reservation.model;

import com.hms.api.common.exception.BusinessException;
import com.hms.api.common.jackson.CodeLabelEnum;
import com.hms.api.common.jackson.CodeLabelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(implementation = CodeLabelResponse.class)
public enum ReservationSource implements CodeLabelEnum {
  HMS_WEB("hms-web", "Aplikacja HMS Web"),
  HMS_MOB("hms-mobile", "Aplikacja HMS Mobile"),
  PHONE("phone", "Telefonicznie"),
  EMAIL("email", "Emailowo"),
  RECEPTION("reception", "Przez recepcję"),
  OTHER("other", "Inne");

  private final String code;
  private final String label;

  ReservationSource(String code, String label) {
    this.code = code;
    this.label = label;
  }

  public static ReservationSource fromCode(String code) {
    if (code == null) {
      throw new BusinessException("Brak źródła rezerwacji");
    }
    for (ReservationSource source : values()) {
      if (source.code.equalsIgnoreCase(code)) {
        return source;
      }
    }
    throw new BusinessException("Nieznane źródło rezerwacji: " + code);
  }
}
