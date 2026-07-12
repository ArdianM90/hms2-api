package com.hms.api.domain.task.repository;

import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TasksRepository {

  TaskDetails getTask(int employeeTaskId);

  List<TaskListItem> getAllTasks(TasksFilterParams filterParams);

  List<MyTaskListItem> getMyTasks(UUID appUserId);

  Integer addTask(AddTaskRequest request);

  void updateTask(Integer employeeTaskId, UpdateTaskRequest request);

  void updateStatus(Integer employeeTaskId, TaskStatus taskStatus, LocalDateTime completedAt);
}
