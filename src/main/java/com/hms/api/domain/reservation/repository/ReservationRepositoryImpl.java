package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.dto.ReservationStatusDto;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.generated.jooq.hms.tables.ReservationsV;
import com.hms.generated.jooq.hms.tables.records.ReservationRecord;
import com.hms.generated.jooq.hms.tables.records.ReservationsVRecord;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final DSLContext dsl;

  @Override
  public void makeReservation(
      String appUserId, ReservationSource source, MakeReservationRequest request) {
    List<ReservationRecord> records = new ArrayList<>();
    for (int roomId : request.roomIds()) {
      ReservationRecord record = new ReservationRecord();
      record.setRoomId(roomId);
      record.setUserId(UUID.fromString(appUserId));
      record.setStartDate(request.dateStart());
      record.setEndDate(request.dateEnd());
      record.setStatusCode(ReservationStatus.CREATED.getCode());
      record.setSourceCode(source.getCode());
      record.setTotalPrice(request.totalPrice());
      record.setComment(request.comment());
      records.add(record);
    }
    dsl.batchInsert(records).execute();
  }

  @Override
  public List<ReservationDto> getReservations(LocalDate startDate, LocalDate endDate) {
    ReservationsV reservations = ReservationsV.RESERVATIONS_V;
    return dsl.selectFrom(reservations)
        .where(reservations.START_DATE.le(endDate).and(reservations.END_DATE.ge(startDate)))
        .fetch(this::mapToReservationDto);
  }

  private ReservationDto mapToReservationDto(ReservationsVRecord r) {
    ReservationsV v = ReservationsV.RESERVATIONS_V;
    return new ReservationDto(
        r.get(v.RESERVATION_ID),
        r.get(v.START_DATE),
        r.get(v.END_DATE),
        new RoomStandard(r.get(v.STANDARD_CODE), r.get(v.STANDARD_NAME)),
        new ReservationStatusDto(r.get(v.STATUS_CODE), r.get(v.STATUS_NAME)),
        r.get(v.ROOM_ID));
  }
}
