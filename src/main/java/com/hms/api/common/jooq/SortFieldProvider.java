package com.hms.api.common.jooq;

import com.hms.api.common.dto.PageableParam;
import java.util.Objects;
import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;

public final class SortFieldProvider {

  private boolean sortDesc;
  private boolean defaultDesc = false;
  private Table<?> table;
  private String sortBy;
  private Field<?> defaultSortField;

  public static SortFieldProvider builder() {
    return new SortFieldProvider();
  }

  public SortFieldProvider table(Table<?> table) {
    this.table = table;
    return this;
  }

  public SortFieldProvider pageable(PageableParam pageable) {
    this.sortBy = pageable.getSortBy();
    this.sortDesc = pageable.isDescending();
    return this;
  }

  public <T> SortFieldProvider defaultField(TableField<?, T> field, Class<T> type) {
    this.defaultSortField = table.field(field.getName(), type);
    return this;
  }

  public SortFieldProvider defaultDesc() {
    this.defaultDesc = true;
    return this;
  }

  public SortField<?>[] build() {
    Objects.requireNonNull(table);
    Objects.requireNonNull(defaultSortField);

    boolean usingDefault = sortBy == null || sortBy.isEmpty();
    Field<?> baseField = getFieldByNameOrDefault(table, defaultSortField, sortBy);
    boolean desc = usingDefault ? defaultDesc : sortDesc;

    SortField<?> sortField = desc ? baseField.desc() : baseField.asc();
    return new SortField[] {sortField};
  }

  private Field<?> getFieldByNameOrDefault(
      Table<?> table, Field<?> defaultField, String fieldName) {
    if (fieldName == null || fieldName.isEmpty()) {
      return defaultField;
    }
    Field<?> baseField = table.field(camelToSnake(fieldName));
    return baseField != null ? baseField : defaultField;
  }

  private String camelToSnake(String str) {
    return str.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
  }
}
