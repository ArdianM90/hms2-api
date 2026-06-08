ALTER TABLE hms.room
    ADD COLUMN type_room_standard_code text;

ALTER TABLE hms.room
    ALTER COLUMN type_room_standard_code SET NOT NULL;

ALTER TABLE hms.room
    DROP CONSTRAINT fk_room_type_room_standard;

ALTER TABLE hms.type_room_standard
    DROP CONSTRAINT type_room_standard_pkey;

ALTER TABLE hms.type_room_standard
    DROP COLUMN type_room_standard_id;

ALTER TABLE hms.type_room_standard
    ADD CONSTRAINT type_room_standard_pkey PRIMARY KEY (code);

ALTER TABLE hms.type_room_standard
    ADD COLUMN is_active boolean NOT NULL DEFAULT true;

ALTER TABLE hms.room
    ADD CONSTRAINT fk_room_type_room_standard_code
        FOREIGN KEY (type_room_standard_code)
            REFERENCES hms.type_room_standard(code);

ALTER TABLE hms.room
    DROP COLUMN type_room_standard_id;