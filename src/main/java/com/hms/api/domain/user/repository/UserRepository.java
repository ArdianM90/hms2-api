package com.hms.api.domain.user.repository;

import com.hms.api.domain.user.dto.EmployeeListItem;
import java.util.List;

public interface UserRepository {

  List<EmployeeListItem> getEmployees();
}
