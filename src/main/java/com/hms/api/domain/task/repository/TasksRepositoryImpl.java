package com.hms.api.domain.task.repository;

import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import com.hms.generated.jooq.hms.tables.EmployeeTask;
import com.hms.generated.jooq.hms.tables.EmployeeTaskV;
import com.hms.generated.jooq.hms.tables.records.EmployeeTaskRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TasksRepositoryImpl implements TasksRepository {

  private final DSLContext dsl;

  @Override
  public List<TaskListItem> getAllTasks(TasksFilterParams filterParams) {
    EmployeeTaskV etv = EmployeeTaskV.EMPLOYEE_TASK_V;
    Condition condition = DSL.trueCondition();
    if (filterParams.userId() != null) {
      condition = condition.and(etv.ASSIGNEE_USER_ID.eq(filterParams.userId()));
    }
    return dsl.select(
            etv.EMPLOYEE_TASK_ID,
            etv.ASSIGNEE_USER_ID,
            etv.ASSIGNEE_FIRST_NAME,
            etv.ASSIGNEE_LAST_NAME,
            etv.CREATED_BY_USER_ID,
            etv.CREATED_BY_FIRST_NAME,
            etv.CREATED_BY_LAST_NAME,
            etv.ROOM_ID,
            etv.ROOM_NUMBER,
            etv.RESERVATION_ID,
            etv.TASK_TYPE_CODE,
            etv.TASK_TYPE,
            etv.STATUS_CODE,
            etv.STATUS,
            etv.TITLE,
            etv.DESCRIPTION,
            etv.PRIORITY,
            etv.DUE_AT,
            etv.CREATED_AT,
            etv.STARTED_AT,
            etv.COMPLETED_AT)
        .from(etv)
        .where(condition)
        .fetchInto(TaskListItem.class);
  }

  @Override
  public List<MyTaskListItem> getMyTasks(UUID appUserId) {
    EmployeeTaskV etv = EmployeeTaskV.EMPLOYEE_TASK_V;
    return dsl.select(
            etv.EMPLOYEE_TASK_ID,
            etv.CREATED_BY_USER_ID,
            etv.CREATED_BY_FIRST_NAME,
            etv.CREATED_BY_LAST_NAME,
            etv.ROOM_NUMBER,
            etv.RESERVATION_ID,
            etv.TASK_TYPE_CODE,
            etv.TASK_TYPE,
            etv.STATUS_CODE,
            etv.STATUS,
            etv.TITLE,
            etv.DESCRIPTION,
            etv.PRIORITY,
            etv.DUE_AT,
            etv.CREATED_AT,
            etv.STARTED_AT,
            etv.COMPLETED_AT)
        .from(etv)
        .where(etv.ASSIGNEE_USER_ID.eq(appUserId))
        .fetchInto(MyTaskListItem.class);
  }

  @Override
  public Integer addTask(AddTaskRequest request) {
    EmployeeTaskRecord taskRecord = dsl.newRecord(EmployeeTask.EMPLOYEE_TASK);
    taskRecord.setAssigneeUserId(request.assigneeUserId());
    taskRecord.setCreatedByUserId(request.createdByUserId());
    taskRecord.setRoomId(request.roomId());
    taskRecord.setReservationId(request.reservationId());
    taskRecord.setTaskTypeCode(request.taskTypeCode().getCode());
    taskRecord.setStatusCode(TaskStatus.ASSIGNED.getCode());
    taskRecord.setTitle(request.title());
    taskRecord.setDescription(request.description());
    taskRecord.setPriority(request.priority());
    taskRecord.setDueAt(request.dueAt());
    taskRecord.store();
    return taskRecord.getEmployeeTaskId();
  }

  @Override
  public void updateTask(Integer employeeTaskId, UpdateTaskRequest request) {
    EmployeeTask et = EmployeeTask.EMPLOYEE_TASK;
    dsl.update(et)
        .set(et.ASSIGNEE_USER_ID, request.assigneeUserId())
        .set(et.CREATED_BY_USER_ID, request.createdByUserId())
        .set(et.ROOM_ID, request.roomId())
        .set(et.RESERVATION_ID, request.reservationId())
        .set(et.TASK_TYPE_CODE, request.taskTypeCode().getCode())
        .set(et.STATUS_CODE, request.statusCode().getCode())
        .set(et.TITLE, request.title())
        .set(et.DESCRIPTION, request.description())
        .set(et.PRIORITY, request.priority())
        .set(et.DUE_AT, request.dueAt())
        .set(et.UPDATED_AT, LocalDateTime.now())
        .where(et.EMPLOYEE_TASK_ID.eq(employeeTaskId))
        .execute();
  }

  @Override
  public void updateStatus(
      Integer employeeTaskId, TaskStatus taskStatus, LocalDateTime completedAt) {
    EmployeeTask et = EmployeeTask.EMPLOYEE_TASK;
    dsl.update(et)
        .set(et.STATUS_CODE, taskStatus.getCode())
        .set(et.COMPLETED_AT, completedAt)
        .set(et.UPDATED_AT, LocalDateTime.now())
        .where(et.EMPLOYEE_TASK_ID.eq(employeeTaskId))
        .execute();
  }
}
