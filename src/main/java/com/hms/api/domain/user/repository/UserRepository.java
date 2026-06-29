package com.hms.api.domain.user.repository;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

  List<EmployeeListItem> getEmployees();

  void updateEmployee(UUID userId, EmployeeRequest request);

  void setEmployeePostions(UUID userId, String[] positionCodes);
}
