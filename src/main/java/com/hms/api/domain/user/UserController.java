package com.hms.api.domain.user;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.domain.user.dto.EmployeeListItem;
import com.hms.api.domain.user.dto.EmployeeRequest;
import com.hms.api.domain.user.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/employees")
  public ResponseEntity<List<EmployeeListItem>> getEmployees() {
    return ResponseEntity.ok(userService.getEmployees());
  }

  @PostMapping("/employees")
  public ResponseEntity<LabeledValue<UUID>> addEmployee(@RequestBody EmployeeRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("userId", userService.addEmployee(request)));
  }

  @PutMapping("/employees/{user-id}")
  public ResponseEntity<Void> updateEmployee(
      @PathVariable("user-id") UUID userId, @RequestBody EmployeeRequest request) {
    userService.updateEmployee(userId, request);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/employees/{user-id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable("user-id") UUID userId) {
    userService.deleteEmployee(userId);
    return ResponseEntity.noContent().build();
  }
}
