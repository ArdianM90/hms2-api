package com.hms.api.domain.task.repository;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TasksRepository {

  TaskDetails getTask(int employeeTaskId);

  PageableResult<List<TaskListItem>> getTasks(
      TasksFilterParams filterParams, PageableParam pageable);

  PageableResult<List<TaskListItem>> getMyTasks(
      UUID appUserId, TasksFilterParams filterParams, PageableParam pageable);

  Integer addTask(AddTaskRequest request);

  void updateTask(Integer employeeTaskId, UpdateTaskRequest request);

  void updateStatus(Integer employeeTaskId, TaskStatus taskStatus, LocalDateTime completedAt);
}
