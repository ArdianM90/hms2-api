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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final AuthContext authContext;
  private final TasksRepository tasksRepository;

  @Override
  public TaskDetails getTask(int employeeTaskId) {
    return tasksRepository.getTask(employeeTaskId);
  }

  @Override
  public PageableResult<List<TaskListItem>> getTasks(
      TasksFilterParams filterParams, PageableParam pageable) {
    if (authContext.isAdmin()) {
      return tasksRepository.getTasks(filterParams, pageable);
    }
    UUID appUserId = authContext.currentUserId();
    return tasksRepository.getMyTasks(appUserId, filterParams, pageable);
  }

  @Override
  public Integer addTask(AddTaskRequest request) {
    if (!authContext.isAdmin()) {
      throw new AccessDeniedException("Wymagana rola administratora");
    }
    return tasksRepository.addTask(request);
  }

  @Override
  public void updateTask(Integer employeeTaskId, UpdateTaskRequest request) {
    if (!authContext.isEmployee()) {
      throw new AccessDeniedException("Wymagana rola pracownika lub administratora");
    }
    tasksRepository.updateTask(employeeTaskId, request);
  }

  @Override
  public void updateStatus(Integer employeeTaskId, UpdateStatusRequest request) {
    if (!authContext.isEmployee()) {
      throw new AccessDeniedException("Wymagana rola pracownika lub administratora");
    }

    LocalDateTime completedAt;
    if (TaskStatus.COMPLETED.equals(request.statusCode()) && request.completedAt() == null) {
      completedAt = LocalDateTime.now();
    } else {
      completedAt = request.completedAt();
    }
    tasksRepository.updateStatus(employeeTaskId, request.statusCode(), completedAt);
  }
}
