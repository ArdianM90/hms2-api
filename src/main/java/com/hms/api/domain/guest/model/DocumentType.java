package com.hms.api.domain.guest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.hms.api.common.exception.BusinessException;
import com.hms.api.common.jackson.CodeLabelEnum;
import com.hms.api.common.jackson.CodeLabelResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(implementation = CodeLabelResponse.class)
public enum DocumentType implements CodeLabelEnum {
  ID_CARD("Dowód osobisty"),
  PASSPORT("Paszport"),
  DRIVING_LICENSE("Prawo jazdy"),
  RESIDENCE_CARD("Karta pobytu"),
  OTHER("Inny dokument");

  private final String label;

  DocumentType(String label) {
    this.label = label;
  }

  public String getCode() {
    return name().toLowerCase();
  }

  @JsonCreator
  public static DocumentType fromCode(String code) {
    for (DocumentType type : values()) {
      if (type.name().equalsIgnoreCase(code)) {
        return type;
      }
    }
    throw new BusinessException("Nierozpoznany kod typu dokumentu: " + code);
  }
}
