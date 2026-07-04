DROP VIEW IF EXISTS hms.employee_task_v;

CREATE VIEW hms.employee_task_v
AS
select et.employee_task_id,
       et.assignee_user_id,
       assignee.first_name as assignee_first_name,
       assignee.last_name  as assignee_last_name,
       et.created_by_user_id,
       creator.first_name  as created_by_first_name,
       creator.last_name   as created_by_last_name,
       et.room_id,
       et.reservation_id,
       et.task_type_code,
       tet."name"          as task_type,
       et.status_code,
       tets."name"         as status,
       et.title,
       et.description,
       et.priority,
       et.due_at,
       et.created_at,
       et.started_at,
       et.completed_at
from hms.employee_task et
         left join hms.type_employee_task tet on et.task_type_code = tet.code
         left join hms.type_employee_task_status tets on et.status_code = tets.code
         left join auth.app_user assignee on et.assignee_user_id = assignee.user_id
         left join auth.app_user creator on et.created_by_user_id = creator.user_id;