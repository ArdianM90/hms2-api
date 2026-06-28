package com.hms.api.domain.user.repository;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.generated.jooq.hms.tables.EmployeeV;
import java.util.List;
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
}
