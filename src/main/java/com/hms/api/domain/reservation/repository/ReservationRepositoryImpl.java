package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.dto.ReservationStatus;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.generated.jooq.hms.tables.ReservationsV;
import com.hms.generated.jooq.hms.tables.records.ReservationsVRecord;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final DSLContext dsl;

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
        new ReservationStatus(r.get(v.STATUS_CODE), r.get(v.STATUS_NAME)),
        r.get(v.ROOM_ID));
  }
}
