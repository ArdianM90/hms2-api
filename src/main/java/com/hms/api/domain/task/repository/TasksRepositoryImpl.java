package com.hms.api.domain.task.repository;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.jooq.PaginatedQueryBuilder;
import com.hms.api.common.jooq.SortFieldProvider;
import com.hms.api.domain.task.dto.*;
import com.hms.api.domain.task.model.TaskStatus;
import com.hms.generated.jooq.hms.tables.EmployeeTask;
import com.hms.generated.jooq.hms.tables.EmployeeTaskV;
import com.hms.generated.jooq.hms.tables.records.EmployeeTaskRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TasksRepositoryImpl implements TasksRepository {

  private final DSLContext dsl;
  private final PathPatternRequestMatcher.Builder builder;

  @Override
  public TaskDetails getTask(int employeeTaskId) {
    EmployeeTaskV etv = EmployeeTaskV.EMPLOYEE_TASK_V;
    return dsl.selectFrom(etv)
        .where(etv.EMPLOYEE_TASK_ID.eq(employeeTaskId))
        .fetchOne(
            r ->
                new TaskDetails(
                    r.getAssigneeUserId(),
                    r.getAssigneeFirstName(),
                    r.getAssigneeLastName(),
                    r.getCreatedByUserId(),
                    r.getCreatedByFirstName(),
                    r.getCreatedByLastName(),
                    r.getRoomId(),
                    r.getRoomNumber(),
                    r.getReservationId(),
                    new DictionaryValue(r.getTaskTypeCode(), r.getTaskType()),
                    new DictionaryValue(r.getStatusCode(), r.getStatus()),
                    r.getTitle(),
                    r.getDescription(),
                    r.getPriority(),
                    r.getDueAt(),
                    r.getCreatedAt(),
                    r.getStartedAt(),
                    r.getCompletedAt()));
  }

  @Override
  public PageableResult<List<TaskListItem>> getTasks(
      UUID appUserId, TasksFilterParams filterParams, PageableParam pageable) {
    EmployeeTaskV etv = EmployeeTaskV.EMPLOYEE_TASK_V;

    RecordMapper<Record, TaskListItem> taskListItemMapper =
        r ->
            new TaskListItem(
                r.get(etv.EMPLOYEE_TASK_ID),
                r.get(etv.ASSIGNEE_USER_ID),
                r.get(etv.ASSIGNEE_FIRST_NAME),
                r.get(etv.ASSIGNEE_LAST_NAME),
                r.get(etv.CREATED_BY_USER_ID),
                r.get(etv.CREATED_BY_FIRST_NAME),
                r.get(etv.CREATED_BY_LAST_NAME),
                r.get(etv.ROOM_ID),
                r.get(etv.ROOM_NUMBER),
                r.get(etv.RESERVATION_ID),
                DictionaryValue.of(r, etv.TASK_TYPE_CODE, etv.TASK_TYPE),
                DictionaryValue.of(r, etv.STATUS_CODE, etv.STATUS),
                r.get(etv.TITLE),
                r.get(etv.DESCRIPTION),
                r.get(etv.PRIORITY),
                r.get(etv.DUE_AT),
                r.get(etv.CREATED_AT),
                r.get(etv.STARTED_AT),
                r.get(etv.COMPLETED_AT));

    Condition condition = DSL.trueCondition();
    if (appUserId != null) {
      condition = condition.and(etv.ASSIGNEE_USER_ID.eq(filterParams.userId()));
    }

    Select<?> select = dsl.selectFrom(etv).where(condition);

    Function<Table<?>, SortField<?>[]> sortFieldProvider =
        table ->
            SortFieldProvider.builder()
                .table(table)
                .pageable(pageable)
                .defaultField(etv.CREATED_AT, LocalDateTime.class)
                .defaultDesc()
                .build();

    return PaginatedQueryBuilder.<TaskListItem>builder()
        .dsl(dsl)
        .baseSelect(select)
        .pageable(pageable)
        .sortFieldProvider(sortFieldProvider)
        .mapper(taskListItemMapper)
        .build()
        .fetch();
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
