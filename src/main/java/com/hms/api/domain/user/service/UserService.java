package com.hms.api.domain.user.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.dto.EmployeesFilterParams;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  PageableResult<List<EmployeeListItem>> getEmployees(
      EmployeesFilterParams filterParams, PageableParam pageable);

  @Transactional
  UUID addEmployee(EmployeeRequest request);

  @Transactional
  void updateEmployee(UUID userId, EmployeeRequest request);

  @Transactional
  void deleteEmployee(UUID userId);
}
