CREATE TABLE hms.type_reservation_status
(
    code      text              NOT NULL PRIMARY KEY,
    name      text              NOT NULL,
    is_active bool DEFAULT true NOT NULL
);

INSERT INTO hms.type_reservation_status (code, name)
VALUES ('created', 'Utworzona'),
       ('confirmed', 'Potwierdzona'),
       ('cancelled', 'Anulowana'),
       ('checked_in', 'Zameldowany'),
       ('checked_out', 'Wymeldowany'),
       ('no_show', 'Nie pojawił się');

CREATE TABLE hms.type_reservation_source
(
    code      text              NOT NULL PRIMARY KEY,
    name      text              NOT NULL,
    is_active bool DEFAULT true NOT NULL
);

INSERT INTO hms.type_reservation_source (code, name)
VALUES ('hms_web', 'Aplikacja HMS Web'),
       ('hms_mob', 'Aplikacja HMS Mobile'),
       ('phone', 'Telefonicznie'),
       ('email', 'Emailowo'),
       ('reception', 'Przez recepcję'),
       ('other', 'Inne');

CREATE TABLE hms.reservation
(
    reservation_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    room_id        INT       NOT NULL,
    user_id        uuid,
    start_date     DATE      NOT NULL,
    end_date       DATE      NOT NULL,
    status_code    TEXT      NOT NULL,
    source_code    TEXT      NOT NULL DEFAULT 'hms_web',
    total_price    NUMERIC(10, 2),
    created_at     TIMESTAMP NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP,
    comment        TEXT,
    CONSTRAINT fk_reservation_reservation_room FOREIGN KEY (room_id) REFERENCES hms.room (room_id),
    CONSTRAINT fk_reservation_app_user FOREIGN KEY (user_id) REFERENCES auth.app_user (user_id),
    CONSTRAINT fk_reservation_reservation_status FOREIGN KEY (status_code) REFERENCES hms.type_reservation_status (code),
    CONSTRAINT fk_reservation_reservation_source FOREIGN KEY (source_code) REFERENCES hms.type_reservation_source (code)
);