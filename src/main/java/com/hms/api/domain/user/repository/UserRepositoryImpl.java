package com.hms.api.domain.user.repository;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.generated.jooq.auth.tables.AppUser;
import com.hms.generated.jooq.hms.tables.EmployeePosition;
import com.hms.generated.jooq.hms.tables.EmployeeV;
import com.hms.generated.jooq.hms.tables.records.EmployeePositionRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final DSLContext dsl;

  @Override
  public List<EmployeeListItem> getEmployees() {
    EmployeeV view = EmployeeV.EMPLOYEE_V;
    return dsl.selectFrom(view)
        .where(view.IS_ACTIVE.isTrue())
        .fetch(
            r ->
                new EmployeeListItem(
                    r.getUserId(),
                    r.getEmail(),
                    r.getFirstName(),
                    r.getLastName(),
                    r.getRoleCode(),
                    r.getPositionCodes()));
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
