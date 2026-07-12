package com.hms.api.common.dto;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageableParam extends SortParam {

  private int page = 1;
  private int pageSize = 10;

  @AssertTrue
  public boolean isValid() {
    return this.page > 0 && this.pageSize > 0 && this.pageSize < 1000;
  }
}
