package com.hms.api.domain.user.service;

import com.hms.api.common.auth.AuthClient;
import com.hms.api.common.auth.dto.RegisterRequest;
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

  private final AuthClient authClient;
  private final UserRepository userRepository;

  @Override
  public List<EmployeeListItem> getEmployees() {
    return userRepository.getEmployees();
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
