drop view if exists hms.reservation_rooms_v;

CREATE VIEW hms.reservation_rooms_v
AS
SELECT rsv.reservation_id,
       rsv.status_code as reservation_status_code,
       rsv.start_date,
       rsv.end_date,
       r.room_id,
       r.room_number,
       r.capacity,
       r.price_per_night,
       trs.code        AS standard_code,
       trs.name        AS standard_name
FROM hms.reservation rsv
         JOIN hms.reservation_room rr ON rsv.reservation_id = rr.reservation_id
         JOIN hms.room r ON rr.room_id = r.room_id
         JOIN hms.type_room_standard trs ON r.type_room_standard_code = trs.code;