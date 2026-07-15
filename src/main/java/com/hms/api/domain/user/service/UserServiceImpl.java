package com.hms.api.domain.user.service;

import com.hms.api.common.auth.AuthClient;
import com.hms.api.common.auth.dto.RegisterRequest;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.dto.EmployeesFilterParams;
import com.hms.api.domain.user.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final AuthClient authClient;
  private final UserRepository userRepository;

  @Override
  public PageableResult<List<EmployeeListItem>> getEmployees(
      EmployeesFilterParams filterParams, PageableParam pageable) {
    return userRepository.getEmployees(filterParams, pageable);
  }

  @Override
  public UUID addEmployee(EmployeeRequest request) {
    RegisterRequest registerRequest =
        new RegisterRequest(
            request.email(), request.firstName(), request.lastName(), request.roleCode());
    UUID userId = authClient.register(registerRequest);
    if (request.positionCodes() != null) {
      userRepository.setEmployeePostions(userId, request.positionCodes());
    }
    return userId;
  }

  @Override
  public void updateEmployee(UUID userId, EmployeeRequest request) {
    userRepository.updateEmployee(userId, request);
  }

  @Override
  public void deleteEmployee(UUID userId) {
    authClient.inactivateUser(userId);
  }
}
