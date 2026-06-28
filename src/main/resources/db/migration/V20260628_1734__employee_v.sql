DROP VIEW IF EXISTS hms.employee_v;

CREATE VIEW hms.employee_v
AS
select au.user_id,
       au.email,
       au.first_name,
       au.last_name,
       au.is_active,
       au.role_code,
       COALESCE(
               (SELECT array_agg(ep.position_code ORDER BY ep.position_code)
                FROM hms.employee_position ep
                WHERE ep.user_id = au.user_id),
               ARRAY []::text[]
       ) AS position_codes
from auth.app_user au
where au.role_code in ('admin', 'employee');