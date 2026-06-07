CREATE SCHEMA hms;

CREATE TABLE hms.type_room_standard
(
    type_room_standard_id int generated always as identity PRIMARY KEY,
    code                  text NOT NULL UNIQUE,
    name                  text NOT NULL
);

INSERT INTO hms.type_room_standard (code, name)
VALUES ('standard', 'Standard'),
       ('comfort', 'Komfort'),
       ('business', 'Biznes'),
       ('double', 'Double'),
       ('premium', 'Premium');

CREATE TABLE hms.room
(
    room_id               int generated always as identity PRIMARY KEY,
    type_room_standard_id int            NOT NULL,
    capacity              int            NOT NULL,
    price_per_night       NUMERIC(10, 2) NOT NULL,
    room_number           text           NOT NULL,
    floor                 INT,
    area_m2               int,
    is_active             BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at            TIMESTAMP      NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP,
    constraint uq_room_number unique (room_number),
    constraint fk_room_type_room_standard foreign key (type_room_standard_id) references hms.type_room_standard (type_room_standard_id)
);

CREATE TABLE hms.room_property
(
    room_property_id int generated always as identity PRIMARY KEY,
    room_id          INT       NOT NULL,
    property_key     text      NOT NULL,
    property_value   TEXT,
    created_at       TIMESTAMP NOT NULL DEFAULT now(),
    updated_at       TIMESTAMP,
    constraint fk_room_property_room foreign key (room_id) references hms.room (room_id) on delete cascade
);