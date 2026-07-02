-- hms.type_employee_task_status
CREATE TABLE hms.type_employee_task_status
(
    code      text PRIMARY KEY,
    name      text                 NOT NULL,
    is_active boolean DEFAULT true NOT NULL
);

INSERT INTO hms.type_employee_task_status(code, name)
VALUES ('assigned', 'Przydzielone'),
       ('in_progress', 'W trakcie'),
       ('completed', 'Wykonane'),
       ('cancelled', 'Anulowane');

-- hms.type_employee_task
CREATE TABLE hms.type_employee_task
(
    code      text PRIMARY KEY,
    name      text                 NOT NULL,
    is_active boolean DEFAULT true NOT NULL
);

INSERT INTO hms.type_employee_task(code, name)
VALUES ('room_service', 'Obsługa pokoju'),
       ('prepare_room', 'Przygotowanie pokoju'),
       ('technical_issue', 'Usunięcie usterki'),
       ('maintenance', 'Prace konserwacyjne'),
       ('security_check', 'Dyżur na stanowisku ochrony'),
       ('reception', 'Obsługa recepcji'),
       ('other', 'Inne');
-- hms.employee_task
CREATE TABLE hms.employee_task
(
    employee_task_id   integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    assignee_user_id   uuid                    NOT NULL,
    created_by_user_id uuid                    NOT NULL,
    room_id            integer                 NULL,
    reservation_id     integer                 NULL,
    task_type_code     text                    NOT NULL,
    status_code        text                    NOT NULL DEFAULT 'assigned',
    title              text                    NOT NULL,
    description        text                    NULL,
    priority           smallint  DEFAULT 2     NOT NULL,
    due_date           timestamp               NULL,
    created_at         timestamp DEFAULT now() NOT NULL,
    started_at         timestamp               NULL,
    completed_at       timestamp               NULL,
    CONSTRAINT fk_employee_task_assignee
        FOREIGN KEY (assignee_user_id)
            REFERENCES auth.app_user (user_id),
    CONSTRAINT fk_employee_task_created_by
        FOREIGN KEY (created_by_user_id)
            REFERENCES auth.app_user (user_id),
    CONSTRAINT fk_employee_task_room
        FOREIGN KEY (room_id)
            REFERENCES hms.room (room_id),
    CONSTRAINT fk_employee_task_reservation
        FOREIGN KEY (reservation_id)
            REFERENCES hms.reservation (reservation_id),
    CONSTRAINT fk_employee_task_type
        FOREIGN KEY (task_type_code)
            REFERENCES hms.type_employee_task (code),
    CONSTRAINT fk_employee_task_status
        FOREIGN KEY (status_code)
            REFERENCES hms.type_employee_task_status (code)
);