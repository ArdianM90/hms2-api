package com.hms.api.domain.user;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.UserListItem;
import com.hms.api.domain.user.dto.UserRequest;
import com.hms.api.domain.user.dto.UsersFilterParams;
import com.hms.api.domain.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<PageableResult<List<UserListItem>>> getUsers(
      @ParameterObject UsersFilterParams filterParams,
      @ParameterObject @Valid PageableParam pageable) {
    return ResponseEntity.ok(userService.getUsers(filterParams, pageable));
  }

  @PostMapping
  public ResponseEntity<LabeledValue<UUID>> addUser(@RequestBody UserRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("userId", userService.addUser(request)));
  }

  @PutMapping("/{user-id}")
  public ResponseEntity<Void> updateUser(
      @PathVariable("user-id") UUID userId, @RequestBody UserRequest request) {
    userService.updateUser(userId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{user-id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("user-id") UUID userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
