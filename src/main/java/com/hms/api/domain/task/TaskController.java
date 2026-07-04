package com.hms.api.domain.task;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.common.jwt.JwtService;
import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final JwtService jwtService;
  private final TaskService taskService;

  @GetMapping()
  public ResponseEntity<List<TaskListItem>> getAllTasks(
      @ParameterObject TasksFilterParams filterParams) {
    return ResponseEntity.ok(taskService.getAllTasks(filterParams));
  }

  @GetMapping("/my")
  public ResponseEntity<List<MyTaskListItem>> getMyTasks(Jwt jwt) {
    UUID appUserId = jwtService.requireAppUserId(jwt);
    return ResponseEntity.ok(taskService.getMyTasks(appUserId));
  }

  @PostMapping()
  public ResponseEntity<LabeledValue<Integer>> addTask(@RequestBody @Valid AddTaskRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("employeeTaskId", taskService.addTask(request)));
  }

  @PutMapping("/{employee-task-id}")
  public ResponseEntity<Void> updateTask(
      @PathVariable("employee-task-id") Integer employeeTaskId,
      @RequestBody @Valid UpdateTaskRequest request) {
    taskService.updateTask(employeeTaskId, request);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{employee-task-id}/status")
  public ResponseEntity<Void> updateStatus(
      @PathVariable("employee-task-id") Integer employeeTaskId,
      @RequestBody @Valid UpdateStatusRequest request) {
    taskService.updateStatus(employeeTaskId, request);
    return ResponseEntity.noContent().build();
  }
}
