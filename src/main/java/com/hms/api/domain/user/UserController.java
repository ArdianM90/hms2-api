package com.hms.api.domain.user;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.dto.EmployeesFilterParams;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<PageableResult<List<EmployeeListItem>>> getUsers(
      @ParameterObject EmployeesFilterParams filterParams,
      @ParameterObject @Valid PageableParam pageable) {
    return ResponseEntity.ok(userService.getUsers(filterParams, pageable));
  }

  @PostMapping
  public ResponseEntity<LabeledValue<UUID>> addEmployee(@RequestBody EmployeeRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("userId", userService.addEmployee(request)));
  }

  @PutMapping("/{user-id}")
  public ResponseEntity<Void> updateEmployee(
      @PathVariable("user-id") UUID userId, @RequestBody EmployeeRequest request) {
    userService.updateEmployee(userId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{user-id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable("user-id") UUID userId) {
    userService.deleteEmployee(userId);
    return ResponseEntity.noContent().build();
  }
}
