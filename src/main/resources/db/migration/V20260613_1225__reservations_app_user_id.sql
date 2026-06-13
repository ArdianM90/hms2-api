alter table hms.reservation
    rename column user_id to app_user_id;

drop view if exists hms.reservations_v;

CREATE OR REPLACE VIEW hms.reservations_v
AS
SELECT rsv.reservation_id,
       rsv.app_user_id,
       rsv.created_at,
       rsv.updated_at,
       rsv.start_date,
       rsv.end_date,
       rsv.status_code,
       t_sts.name AS status_name,
       rsv.source_code,
       t_src.name AS source_name,
       rsv.total_price,
       count(*)   AS rooms_quantity
FROM hms.reservation rsv
         JOIN hms.type_reservation_status t_sts ON rsv.status_code = t_sts.code
         JOIN hms.type_reservation_source t_src ON rsv.source_code = t_src.code
         JOIN hms.reservation_room rr ON rsv.reservation_id = rr.reservation_id
         JOIN hms.room r ON r.room_id = rr.room_id
GROUP BY rsv.reservation_id, rsv.created_at, rsv.updated_at, rsv.start_date, rsv.end_date, rsv.status_code, t_sts.name,
         rsv.source_code, t_src.name, rsv.total_price;