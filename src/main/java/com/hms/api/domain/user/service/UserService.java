package com.hms.api.domain.user.service;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import java.util.List;
import java.util.UUID;

public interface UserService {

  List<EmployeeListItem> getEmployees();

  UUID addEmployee(EmployeeRequest request);

  void updateEmployee(UUID userId, EmployeeRequest request);

  void deleteEmployee(UUID userId);
}
