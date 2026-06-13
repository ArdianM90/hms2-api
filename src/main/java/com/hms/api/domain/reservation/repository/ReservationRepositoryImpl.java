package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import com.hms.generated.jooq.hms.Tables;
import com.hms.generated.jooq.hms.tables.Reservation;
import com.hms.generated.jooq.hms.tables.ReservationRoomsV;
import com.hms.generated.jooq.hms.tables.ReservationsV;
import com.hms.generated.jooq.hms.tables.records.ReservationRecord;
import com.hms.generated.jooq.hms.tables.records.ReservationRoomRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final DSLContext dsl;

  @Override
  public List<ReservationDto> getMyReservations(UUID appUserId) {
    ReservationsV v = ReservationsV.RESERVATIONS_V;
    return dsl.select(
            v.RESERVATION_ID,
            v.START_DATE,
            v.END_DATE,
            v.CREATED_AT,
            v.UPDATED_AT,
            v.STATUS_CODE,
            v.SOURCE_CODE,
            v.TOTAL_PRICE,
            v.ROOMS_QUANTITY)
        .from(v)
        .where(v.APP_USER_ID.eq(appUserId))
        .fetch(
            r ->
                new ReservationDto(
                    r.get(v.RESERVATION_ID),
                    r.get(v.START_DATE),
                    r.get(v.END_DATE),
                    r.get(v.CREATED_AT),
                    r.get(v.UPDATED_AT),
                    ReservationStatus.fromCode(r.get(v.STATUS_CODE)),
                    ReservationSource.fromCode(r.get(v.SOURCE_CODE)),
                    r.get(v.TOTAL_PRICE),
                    r.get(v.ROOMS_QUANTITY)));
  }

  @Override
  public int makeReservation(
      UUID appUserId, ReservationSource source, MakeReservationRequest request) {
    ReservationRecord rsvRecord = dsl.newRecord(Tables.RESERVATION);
    rsvRecord.setAppUserId(appUserId);
    rsvRecord.setStartDate(request.dateStart());
    rsvRecord.setEndDate(request.dateEnd());
    rsvRecord.setStatusCode(ReservationStatus.CREATED.getCode());
    rsvRecord.setSourceCode(source.getCode());
    rsvRecord.setTotalPrice(request.totalPrice());
    rsvRecord.setComment(request.comment());
    rsvRecord.store();

    int reservationId = Objects.requireNonNull(rsvRecord.getReservationId());
    List<ReservationRoomRecord> roomRecords = new ArrayList<>();
    for (int roomId : request.roomIds()) {
      ReservationRoomRecord record = dsl.newRecord(Tables.RESERVATION_ROOM);
      record.setReservationId(reservationId);
      record.setRoomId(roomId);
      roomRecords.add(record);
    }
    dsl.batchInsert(roomRecords).execute();
    return reservationId;
  }

  @Override
  public Set<Integer> getReservedRoomIds(LocalDate startDate, LocalDate endDate) {
    ReservationRoomsV v = ReservationRoomsV.RESERVATION_ROOMS_V;
    return dsl.select(v.ROOM_ID)
        .from(v)
        .where(v.START_DATE.le(endDate).and(v.END_DATE.ge(startDate)))
        .fetchSet(v.ROOM_ID);
  }

  @Override
  public void updateReservationStatus(int reservationId, ReservationStatus status) {
    Reservation r = Tables.RESERVATION;
    dsl.update(r)
        .set(r.STATUS_CODE, status.getCode())
        .set(r.UPDATED_AT, LocalDateTime.now())
        .where(r.RESERVATION_ID.eq(reservationId))
        .execute();
  }
}
