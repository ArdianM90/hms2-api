package com.hms.api.domain.task;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.security.RequireAdmin;
import com.hms.api.common.security.RequireEmployee;
import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequireEmployee
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/{task-id}")
  public ResponseEntity<TaskDetails> getTask(@PathVariable("task-id") int employeeTaskId) {
    return ResponseEntity.ok(taskService.getTask(employeeTaskId));
  }

  @GetMapping()
  public ResponseEntity<PageableResult<List<TaskListItem>>> getTasks(
      @ParameterObject TasksFilterParams filterParams, @ParameterObject PageableParam pageable) {
    return ResponseEntity.ok(taskService.getTasks(filterParams, pageable));
  }

  @PostMapping()
  @RequireAdmin
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
