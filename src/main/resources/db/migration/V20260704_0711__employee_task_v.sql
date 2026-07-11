DROP VIEW IF EXISTS hms.employee_task_v;

CREATE VIEW hms.employee_task_v
AS
SELECT et.employee_task_id,
       et.assignee_user_id,
       assignee.first_name AS assignee_first_name,
       assignee.last_name  AS assignee_last_name,
       et.created_by_user_id,
       creator.first_name  AS created_by_first_name,
       creator.last_name   AS created_by_last_name,
       et.room_id,
       r.room_number,
       et.reservation_id,
       et.task_type_code,
       tet.name            AS task_type,
       et.status_code,
       tets.name           AS status,
       et.title,
       et.description,
       et.priority,
       et.due_at,
       et.created_at,
       et.started_at,
       et.completed_at
FROM hms.employee_task et
         LEFT JOIN hms.type_employee_task tet ON et.task_type_code = tet.code
         LEFT JOIN hms.type_employee_task_status tets ON et.status_code = tets.code
         LEFT JOIN auth.app_user assignee ON et.assignee_user_id = assignee.user_id
         LEFT JOIN auth.app_user creator ON et.created_by_user_id = creator.user_id
         LEFT JOIN hms.room r ON et.room_id = r.room_id;