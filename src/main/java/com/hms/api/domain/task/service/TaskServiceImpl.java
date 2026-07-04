package com.hms.api.domain.task.service;

import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import com.hms.api.domain.task.repository.TasksRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TasksRepository tasksRepository;

  @Override
  public List<TaskListItem> getTasks(TasksFilterParams filterParams) {
    return tasksRepository.getTasks(filterParams);
  }

  @Override
  public Integer addTask(AddTaskRequest request) {
    return tasksRepository.addTask(request);
  }

  @Override
  public void updateTask(Integer employeeTaskId, UpdateTaskRequest request) {
    tasksRepository.updateTask(employeeTaskId, request);
  }

  @Override
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
