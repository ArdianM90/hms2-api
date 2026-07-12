package com.hms.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageableResult<T> {

  private T results;
  private int total;
}
