package com.hms.api.domain.user.service;

import com.hms.api.common.auth.AuthClient;
import com.hms.api.common.auth.dto.RegisterRequest;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.UserListItem;
import com.hms.api.domain.user.dto.UserRequest;
import com.hms.api.domain.user.dto.UsersFilterParams;
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

  private static final String EMPLOYEE_ROLE_CODE = "employee";

  private final AuthClient authClient;
  private final UserRepository userRepository;

  @Override
  public PageableResult<List<UserListItem>> getUsers(
      UsersFilterParams filterParams, PageableParam pageable) {
    return userRepository.getUsers(filterParams, pageable);
  }

  @Override
  public UUID addUser(UserRequest request) {
    RegisterRequest registerRequest =
        new RegisterRequest(
            request.email(), request.firstName(), request.lastName(), request.roleCode());
    UUID userId = authClient.register(registerRequest);
    if (EMPLOYEE_ROLE_CODE.equals(request.roleCode()) && request.positionCodes() != null) {
      userRepository.setEmployeePostions(userId, request.positionCodes());
    }
    return userId;
  }

  @Override
  public void updateUser(UUID userId, UserRequest request) {
    userRepository.updateUser(userId, request);
  }

  @Override
  public void deleteUser(UUID userId) {
    authClient.inactivateUser(userId);
  }
}
