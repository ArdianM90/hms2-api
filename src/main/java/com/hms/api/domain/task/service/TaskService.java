package com.hms.api.domain.task.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.task.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {

  TaskDetails getTask(int employeeTaskId);

  PageableResult<List<TaskListItem>> getTasks(
      TasksFilterParams filterParams, PageableParam pageable);

  @Transactional
  Integer addTask(AddTaskRequest request);

  @Transactional
  void updateTask(Integer employeeTaskId, UpdateTaskRequest request);

  @Transactional
  void updateStatus(Integer employeeTaskId, UpdateStatusRequest request);
}
