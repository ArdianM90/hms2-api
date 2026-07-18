package com.hms.api.domain.user.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.UserListItem;
import com.hms.api.domain.user.dto.UserRequest;
import com.hms.api.domain.user.dto.UsersFilterParams;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  PageableResult<List<UserListItem>> getUsers(
      UsersFilterParams filterParams, PageableParam pageable);

  @Transactional
  UUID addUser(UserRequest request);

  @Transactional
  void updateUser(UUID userId, UserRequest request);

  @Transactional
  void deleteUser(UUID userId);
}
