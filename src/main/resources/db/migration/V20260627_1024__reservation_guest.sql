CREATE TABLE hms.type_document_type
(
    code      text              NOT NULL,
    name      text              NOT NULL,
    is_active bool DEFAULT true NOT NULL,
    CONSTRAINT type_document_type_pkey PRIMARY KEY (code)
);

INSERT INTO hms.type_document_type (code, name)
VALUES ('id_card', 'Dowód osobisty'),
       ('passport', 'Paszport'),
       ('driving_license', 'Prawo jazdy'),
       ('residence_card', 'Karta pobytu'),
       ('other', 'Inny dokument');

CREATE TABLE hms.type_citizenship
(
    code      text              NOT NULL,
    name      text              NOT NULL,
    is_active bool DEFAULT true NOT NULL,
    CONSTRAINT type_citizenship_pkey PRIMARY KEY (code)
);

INSERT INTO hms.type_citizenship (code, name)
VALUES ('PL', 'Polska'),
       ('DE', 'Niemcy'),
       ('CZ', 'Czechy'),
       ('SK', 'Słowacja'),
       ('UA', 'Ukraina'),
       ('GB', 'Wielka Brytania'),
       ('US', 'Stany Zjednoczone'),
       ('FR', 'Francja'),
       ('IT', 'Włochy'),
       ('ES', 'Hiszpania'),
       ('OTHER', 'Inne');

CREATE TABLE hms.reservation_guest
(
    reservation_guest_id integer GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    reservation_id       integer   NOT NULL,
    room_id              integer   NOT NULL,
    first_name           text      NOT NULL,
    last_name            text      NOT NULL,
    pesel                text,
    date_of_birth        date      NOT NULL,
    document_type_code   text      NOT NULL,
    document_number      text      NULL,
    citizenship_code     text,
    phone                text      NULL,
    checked_in_at        timestamp NULL,
    checked_out_at       timestamp NULL,
    CONSTRAINT fk_reservation_guest_guest_reservation FOREIGN KEY (reservation_id) REFERENCES hms.reservation (reservation_id),
    CONSTRAINT fk_reservation_guest_guest_room FOREIGN KEY (room_id) REFERENCES hms.room (room_id),
    CONSTRAINT fk_reservation_guest_document_type FOREIGN KEY (document_type_code) REFERENCES hms.type_document_type (code),
    CONSTRAINT fk_reservation_guest_citizenship FOREIGN KEY (citizenship_code) REFERENCES hms.type_citizenship (code)
);