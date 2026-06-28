CREATE TABLE auth.type_app_user_role
(
    code      text PRIMARY KEY,
    name      text    NOT NULL,
    is_active boolean NOT NULL DEFAULT true
);

INSERT INTO auth.type_app_user_role(code, name)
VALUES ('admin', 'Administrator'),
       ('employee', 'Pracownik'),
       ('guest', 'Gość');

ALTER TABLE auth.app_user
    ADD COLUMN role_code text NOT NULL DEFAULT 'guest',
    ADD CONSTRAINT fk_app_user_role
        FOREIGN KEY (role_code)
            REFERENCES auth.type_app_user_role (code);

update auth.app_user
set role_code = 'admin'
where email = 'admin_test@gmail.com';