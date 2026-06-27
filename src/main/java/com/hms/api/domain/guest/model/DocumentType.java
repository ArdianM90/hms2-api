package com.hms.api.domain.guest.model;

import com.hms.api.common.exception.BusinessException;
import lombok.Getter;

@Getter
public enum DocumentType {
  ID_CARD,
  PASSPORT,
  DRIVING_LICENSE,
  RESIDENCE_CARD,
  OTHER;

  public String getCode() {
    return name().toLowerCase();
  }

  public static DocumentType fromCode(String code) {
    for (DocumentType type : values()) {
      if (type.name().equalsIgnoreCase(code)) {
        return type;
      }
    }
    throw new BusinessException("Nierozpoznany kod typu dokumentu: " + code);
  }
}
