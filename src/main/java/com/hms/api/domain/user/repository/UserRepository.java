package com.hms.api.domain.user.repository;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.UserListItem;
import com.hms.api.domain.user.dto.UserRequest;
import com.hms.api.domain.user.dto.UsersFilterParams;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

  PageableResult<List<UserListItem>> getUsers(
      UsersFilterParams filterParams, PageableParam pageable);

  void updateUser(UUID userId, UserRequest request);

  void setEmployeePostions(UUID userId, String[] positionCodes);
}
