CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE auth."user"
    ALTER COLUMN user_id
        SET DEFAULT gen_random_uuid();

ALTER TABLE auth."user"
    RENAME TO app_user;

insert into auth.app_user(username, email, password_hash)
values ('Admin', 'admin_test@gmail.com', '$2a$10$ZeNN1kqfePf/RrLnozyZF.9XyccHube/rJjeXNO35TiSJlE.OhDDe');
-- hasło: Test1234