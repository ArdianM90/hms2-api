ALTER TABLE auth.app_user
    DROP CONSTRAINT user_username_key,
    DROP COLUMN username,
    ADD COLUMN first_name text,
    ADD COLUMN last_name  text;

update auth.app_user
set first_name = 'Adrian';
update auth.app_user
set last_name = 'M';;

ALTER TABLE auth.app_user
    ALTER COLUMN first_name SET NOT NULL,
    ALTER COLUMN last_name SET NOT NULL;