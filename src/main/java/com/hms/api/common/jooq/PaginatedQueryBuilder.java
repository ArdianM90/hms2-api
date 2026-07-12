package com.hms.api.common.jooq;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import org.jooq.*;
import org.jooq.Record;

@Getter
@Builder
public final class PaginatedQueryBuilder<T> {

  private final DSLContext dsl;
  private final Select<?> baseSelect;
  private final PageableParam pageable;
  private final Function<Table<?>, SortField<?>[]> sortFieldProvider;
  private final Class<T> type;
  private final RecordMapper<Record, T> mapper;

  public PageableResult<List<T>> fetch() {
    int total = dsl.fetchCount(baseSelect);
    if (total == 0) {
      return PaginationUtils.paginatedEmptyResult();
    }

    Table<?> table = baseSelect.asTable("f");
    SortField<?>[] sortFields = sortFieldProvider.apply(table);

    ResultQuery<?> query =
        dsl.selectFrom(table)
            .orderBy(sortFields)
            .limit(pageable.getPageSize())
            .offset(PaginationUtils.offset(pageable));

    List<T> result = mapper != null ? query.fetch(mapper) : query.fetchInto(type);

    return PaginationUtils.paginatedResult(total, result);
  }
}
