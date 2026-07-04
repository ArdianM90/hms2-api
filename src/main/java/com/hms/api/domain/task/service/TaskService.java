package com.hms.api.domain.task.service;

import com.hms.api.domain.task.dto.*;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {

  List<TaskListItem> getAllTasks(TasksFilterParams filterParams);

  List<MyTaskListItem> getMyTasks(UUID appUserId);

  @Transactional
  Integer addTask(AddTaskRequest request);

  @Transactional
  void updateTask(Integer employeeTaskId, UpdateTaskRequest request);

  @Transactional
  void updateStatus(Integer employeeTaskId, UpdateStatusRequest request);
}
