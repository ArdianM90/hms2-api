package com.hms.api.domain.task.repository;

import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface TasksRepository {

  List<TaskListItem> getTasks(TasksFilterParams filterParams);

  Integer addTask(AddTaskRequest request);

  void updateTask(Integer employeeTaskId, UpdateTaskRequest request);

  void updateStatus(Integer employeeTaskId, TaskStatus taskStatus, LocalDateTime completedAt);
}
