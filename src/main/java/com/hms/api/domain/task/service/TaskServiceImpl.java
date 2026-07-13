package com.hms.api.domain.task.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.security.AuthContext;
import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import com.hms.api.domain.task.repository.TasksRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final AuthContext authContext;
  private final TasksRepository tasksRepository;

  @Override
  @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
  public TaskDetails getTask(int employeeTaskId) {
    return tasksRepository.getTask(employeeTaskId);
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
  public PageableResult<List<TaskListItem>> getTasks(
      TasksFilterParams filterParams, PageableParam pageable) {
    if (authContext.isAdmin()) {
      return tasksRepository.getTasks(filterParams, pageable);
    }
    UUID appUserId = authContext.currentUserId();
    return tasksRepository.getMyTasks(appUserId, filterParams, pageable);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public Integer addTask(AddTaskRequest request) {
    return tasksRepository.addTask(request);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public void updateTask(Integer employeeTaskId, UpdateTaskRequest request) {
    tasksRepository.updateTask(employeeTaskId, request);
  }

  @Override
  @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
  public void updateStatus(Integer employeeTaskId, UpdateStatusRequest request) {

    LocalDateTime completedAt;
    if (TaskStatus.COMPLETED.equals(request.statusCode()) && request.completedAt() == null) {
      completedAt = LocalDateTime.now();
    } else {
      completedAt = request.completedAt();
    }
    tasksRepository.updateStatus(employeeTaskId, request.statusCode(), completedAt);
  }
}
