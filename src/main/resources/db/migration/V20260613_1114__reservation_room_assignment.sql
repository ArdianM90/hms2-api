TRUNCATE TABLE hms.reservation CASCADE;

DROP VIEW IF EXISTS hms.reservations_v;

CREATE TABLE hms.reservation_room
(
    reservation_id int4 NOT NULL,
    room_id        int4 NOT NULL,
    CONSTRAINT reservation_room_pkey
        PRIMARY KEY (reservation_id, room_id),
    CONSTRAINT fk_reservation_room_reservation FOREIGN KEY (reservation_id) REFERENCES hms.reservation (reservation_id) ON DELETE CASCADE,
    CONSTRAINT fk_reservation_room_room FOREIGN KEY (room_id) REFERENCES hms.room (room_id)
);

ALTER TABLE hms.reservation
    DROP CONSTRAINT fk_reservation_reservation_room;

ALTER TABLE hms.reservation
    DROP COLUMN room_id;

CREATE VIEW hms.reservations_v AS
SELECT rsv.reservation_id,
       rsv.created_at,
       rsv.updated_at,
       rsv.start_date,
       rsv.end_date,
       rsv.status_code,
       t_sts.name as status_name,
       rsv.source_code,
       t_src.name as source_name,
       rsv.total_price,
       count(*)   as rooms_quantity
FROM hms.reservation rsv
         JOIN hms.type_reservation_status t_sts on rsv.status_code = t_sts.code
         JOIN hms.type_reservation_source t_src on rsv.source_code = t_src.code
         JOIN hms.reservation_room rr on rsv.reservation_id = rr.reservation_id
         JOIN hms.room r ON r.room_id = rr.room_id
GROUP BY rsv.reservation_id,
         rsv.created_at,
         rsv.updated_at,
         rsv.start_date,
         rsv.end_date,
         rsv.status_code,
         t_sts.name,
         rsv.source_code,
         t_src.name,
         rsv.total_price;

CREATE VIEW hms.reservation_rooms_v AS
SELECT rr.reservation_id,
       r.room_id,
       r.room_number,
       r.capacity,
       r.price_per_night,
       trs.code AS standard_code,
       trs.name AS standard_name
FROM hms.reservation_room rr
         JOIN hms.room r
              ON rr.room_id = r.room_id
         JOIN hms.type_room_standard trs
              ON r.type_room_standard_code = trs.code;