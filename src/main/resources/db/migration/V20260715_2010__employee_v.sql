drop view if exists hms.employee_v;

CREATE OR REPLACE VIEW hms.employee_v
AS
SELECT user_id,
       email,
       first_name,
       last_name,
       is_active,
       role_code,
       COALESCE((SELECT array_agg(ep.position_code ORDER BY ep.position_code) AS array_agg
                 FROM hms.employee_position ep
                 WHERE ep.user_id = au.user_id), ARRAY []::text[]) AS position_codes
FROM auth.app_user au;