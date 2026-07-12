package com.hms.api.common.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SortParam {

  private String sortBy;
  private boolean descending;
}
