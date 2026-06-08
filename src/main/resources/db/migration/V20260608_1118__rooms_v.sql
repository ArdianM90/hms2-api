CREATE VIEW hms.room_v AS
SELECT r.room_id,
       r.room_number,
       r.capacity,
       r.price_per_night,
       r.floor,
       r.area_m2,
       t.code AS standard_code,
       t.name AS standard_name
FROM hms.room r
         JOIN hms.type_room_standard t
              ON r.type_room_standard_code = t.code;