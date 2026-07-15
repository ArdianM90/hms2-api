package com.hms.api.domain.user.repository;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.jooq.PaginatedQueryBuilder;
import com.hms.api.common.jooq.SortFieldProvider;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.dto.EmployeesFilterParams;
import com.hms.generated.jooq.auth.tables.AppUser;
import com.hms.generated.jooq.hms.tables.EmployeePosition;
import com.hms.generated.jooq.hms.tables.EmployeeV;
import com.hms.generated.jooq.hms.tables.records.EmployeePositionRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final DSLContext dsl;

  @Override
  public PageableResult<List<EmployeeListItem>> getEmployees(
      EmployeesFilterParams filterParams, PageableParam pageable) {
    EmployeeV view = EmployeeV.EMPLOYEE_V;

    RecordMapper<Record, EmployeeListItem> listItemMapper =
        r ->
            new EmployeeListItem(
                r.get(view.USER_ID),
                r.get(view.EMAIL),
                r.get(view.FIRST_NAME),
                r.get(view.LAST_NAME),
                r.get(view.ROLE_CODE),
                r.get(view.POSITION_CODES));

    Select<?> select = dsl.selectFrom(view).where(buidlEmployeesCondition(filterParams));

    Function<Table<?>, SortField<?>[]> sortFieldProvider =
        table ->
            SortFieldProvider.builder()
                .table(table)
                .pageable(pageable)
                .defaultField(view.LAST_NAME, String.class)
                .defaultDesc()
                .build();

    return PaginatedQueryBuilder.<EmployeeListItem>builder()
        .dsl(dsl)
        .baseSelect(select)
        .pageable(pageable)
        .sortFieldProvider(sortFieldProvider)
        .mapper(listItemMapper)
        .build()
        .fetch();
  }

  private Condition buidlEmployeesCondition(EmployeesFilterParams filterParams) {
    EmployeeV view = EmployeeV.EMPLOYEE_V;
    Condition condition = view.IS_ACTIVE.isTrue();
    if (filterParams == null) {
      return condition;
    }

    if (filterParams.query() != null) {
      String pattern = "%" + filterParams.query().trim() + "%";
      condition =
          condition.and(
              DSL.concat(view.FIRST_NAME, DSL.inline(" "), view.LAST_NAME).likeIgnoreCase(pattern));
    }
    if (filterParams.roleCode() != null) {
      condition = condition.and(view.ROLE_CODE.eq(filterParams.roleCode()));
    }
    if (filterParams.positionCodes() != null) {
      condition =
          condition.and(
              DSL.arrayOverlap(
                  view.POSITION_CODES, filterParams.positionCodes().toArray(String[]::new)));
    }
    return condition;
  }

  @Override
  public void updateEmployee(UUID userId, EmployeeRequest request) {
    AppUser au = AppUser.APP_USER;
    dsl.update(au)
        .set(au.EMAIL, request.email())
        .set(au.FIRST_NAME, request.firstName())
        .set(au.LAST_NAME, request.lastName())
        .set(au.LAST_NAME, request.lastName())
        .set(au.ROLE_CODE, request.roleCode())
        .where(au.USER_ID.eq(userId))
        .execute();
    setEmployeePostions(userId, request.positionCodes());
  }

  @Override
  public void setEmployeePostions(UUID userId, String[] positionCodes) {
    dsl.deleteFrom(EmployeePosition.EMPLOYEE_POSITION)
        .where(EmployeePosition.EMPLOYEE_POSITION.USER_ID.eq(userId))
        .execute();

    List<EmployeePositionRecord> positionRecords = new ArrayList<>();
    for (String positionCode : positionCodes) {
      EmployeePositionRecord positionRecord = new EmployeePositionRecord();
      positionRecord.setUserId(userId);
      positionRecord.setPositionCode(positionCode);
      positionRecords.add(positionRecord);
    }
    dsl.batchInsert(positionRecords).execute();
  }
}
