ALTER TABLE auth.app_user
    DROP CONSTRAINT user_username_key,
    DROP COLUMN username,
    ADD COLUMN first_name text,
    ADD COLUMN last_name  text;

update auth.app_user
set first_name = 'Adrian'
where user_id = 'eff3c11a-0409-498a-abdd-33e7b935de3d';
update auth.app_user
set last_name = 'M'
where user_id = 'eff3c11a-0409-498a-abdd-33e7b935de3d';

ALTER TABLE auth.app_user
    ALTER COLUMN first_name SET NOT NULL,
    ALTER COLUMN last_name SET NOT NULL;