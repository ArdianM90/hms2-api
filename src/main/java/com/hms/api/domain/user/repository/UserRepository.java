package com.hms.api.domain.user.repository;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.dto.EmployeesFilterParams;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

  PageableResult<List<EmployeeListItem>> getEmployees(
      EmployeesFilterParams filterParams, PageableParam pageable);

  void updateEmployee(UUID userId, EmployeeRequest request);

  void setEmployeePostions(UUID userId, String[] positionCodes);
}
