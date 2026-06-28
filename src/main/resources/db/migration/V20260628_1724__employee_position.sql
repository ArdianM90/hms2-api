CREATE TABLE hms.type_employee_position
(
    code      text    NOT NULL,
    name      text    NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    CONSTRAINT type_employee_position_pkey PRIMARY KEY (code)
);

INSERT INTO hms.type_employee_position (code, name)
VALUES ('receptionist', 'Recepcjonista'),
       ('housekeeper', 'Pracownik sprzątający'),
       ('manager', 'Kierownik'),
       ('maintenance', 'Konserwator'),
       ('security', 'Ochrona');

CREATE TABLE hms.employee_position
(
    user_id       uuid NOT NULL,
    position_code text NOT NULL,
    CONSTRAINT employee_position_pkey
        PRIMARY KEY (user_id, position_code),
    CONSTRAINT fk_employee_position_app_user
        FOREIGN KEY (user_id)
            REFERENCES auth.app_user (user_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_employee_position_type
        FOREIGN KEY (position_code)
            REFERENCES hms.type_employee_position (code)
);