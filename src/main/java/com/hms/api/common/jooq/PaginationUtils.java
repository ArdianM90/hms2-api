package com.hms.api.common.jooq;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import java.util.List;

public class PaginationUtils {

  public static <T> PageableResult<List<T>> paginatedResult(int total, List<T> items) {
    return new PageableResult<>(items, total);
  }

  public static <T> PageableResult<List<T>> paginatedEmptyResult() {
    return new PageableResult<>(List.of(), 0);
  }

  public static int offset(PageableParam pageable) {
    return pageable.getPageSize() * (pageable.getPage() - 1);
  }
}
