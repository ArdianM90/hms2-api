ALTER TABLE hms.employee_task
    RENAME COLUMN due_date TO due_at;

ALTER TABLE hms.employee_task
    ADD COLUMN updated_at timestamp NULL;