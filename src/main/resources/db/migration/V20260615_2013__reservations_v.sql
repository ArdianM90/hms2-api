DROP VIEW IF EXISTS hms.reservations_v;

CREATE VIEW hms.reservations_v
AS
SELECT rsv.reservation_id,
       rsv.app_user_id,
       au.first_name                                  as guest_first_name,
       au.last_name                                   as guest_last_name,
       rsv.created_at,
       rsv.updated_at,
       rsv.start_date,
       rsv.end_date,
       rsv.status_code,
       t_sts.name                                     AS status_name,
       rsv.source_code,
       t_src.name                                     AS source_name,
       rsv.total_price,
       (select count(1)
        from hms.reservation_room rr
        where rr.reservation_id = rsv.reservation_id) as rooms_quantity,
       rsv.comment
FROM hms.reservation rsv
         JOIN hms.type_reservation_status t_sts ON rsv.status_code = t_sts.code
         JOIN hms.type_reservation_source t_src ON rsv.source_code = t_src.code
         LEFT JOIN auth.app_user au on rsv.app_user_id = au.user_id;