package com.hms.api.domain.reservation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import com.hms.api.common.jackson.CodeNameEnum;
import com.hms.api.common.jackson.CodeNameResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(implementation = CodeNameResponse.class)
public enum ReservationSource implements CodeNameEnum {
  HMS_WEB("hms-web", "Aplikacja HMS Web"),
  HMS_MOB("hms-mobile", "Aplikacja HMS Mobile"),
  PHONE("phone", "Telefonicznie"),
  EMAIL("email", "Emailowo"),
  RECEPTION("reception", "Przez recepcję"),
  OTHER("other", "Inne");

  private final String code;
  private final String name;

  ReservationSource(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @JsonCreator
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
