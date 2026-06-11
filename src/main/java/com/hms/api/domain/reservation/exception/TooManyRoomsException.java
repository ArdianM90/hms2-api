package com.hms.api.domain.reservation.exception;

import com.hms.api.common.exception.BusinessException;

public class TooManyRoomsException extends BusinessException {
  public TooManyRoomsException(String message) {
    super(message);
  }
}
