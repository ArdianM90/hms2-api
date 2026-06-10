CREATE VIEW hms.reservations_v AS
SELECT rsv.reservation_id,
       rsv.start_date,
       rsv.end_date,
       rsv.status_code,
       trs.name as status_name,
       r.room_id,
       r.room_number,
       r.capacity,
       r.price_per_night,
       t.code   AS standard_code,
       t.name   AS standard_name
FROM hms.reservation rsv
         JOIN hms.type_reservation_status trs on rsv.status_code = trs.code
         JOIN hms.room r
              ON r.room_id = rsv.room_id
         JOIN hms.type_room_standard t
              ON r.type_room_standard_code = t.code;