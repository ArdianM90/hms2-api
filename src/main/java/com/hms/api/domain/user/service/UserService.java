package com.hms.api.domain.user.service;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  List<EmployeeListItem> getEmployees();

  @Transactional
  UUID addEmployee(EmployeeRequest request);

  @Transactional
  void updateEmployee(UUID userId, EmployeeRequest request);

  @Transactional
  void deleteEmployee(UUID userId);
}
