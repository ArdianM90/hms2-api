package com.hms.api.domain.user.service;

import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public List<EmployeeListItem> getEmployees() {
    return userRepository.getEmployees();
  }

  @Override
  public UUID addEmployee(EmployeeRequest request) {
    return null;
  }

  @Override
  public void updateEmployee(UUID userId, EmployeeRequest request) {}

  @Override
  public void deleteEmployee(UUID userId) {}
}
