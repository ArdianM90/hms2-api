package com.hms.api.domain.reservation.repository;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.jooq.PaginatedQueryBuilder;
import com.hms.api.common.jooq.SortFieldProvider;
import com.hms.api.domain.reservation.dto.*;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.generated.jooq.hms.Tables;
import com.hms.generated.jooq.hms.tables.*;
import com.hms.generated.jooq.hms.tables.records.ReservationRecord;
import com.hms.generated.jooq.hms.tables.records.ReservationRoomRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

  private final DSLContext dsl;

  private static final String RESERVATION_CANCELLED_STATUS_CODE = "cancelled";

  @Override
  public ReservationDetails getReservation(int reservationId) {
    ReservationsV rv = ReservationsV.RESERVATIONS_V;
    List<RoomDto> rooms = getReservationRooms(reservationId);
    return dsl.selectFrom(rv)
        .where(rv.RESERVATION_ID.eq(reservationId))
        .fetchOne(
            r ->
                new ReservationDetails(
                    r.get(rv.RESERVATION_ID),
                    r.get(rv.CREATED_AT),
                    r.get(rv.UPDATED_AT),
                    r.get(rv.START_DATE),
                    r.get(rv.END_DATE),
                    r.get(rv.TOTAL_PRICE),
                    DictionaryValue.of(r, rv.STATUS_CODE, rv.STATUS_NAME),
                    DictionaryValue.of(r, rv.SOURCE_CODE, rv.SOURCE_NAME),
                    rooms,
                    r.get(rv.COMMENT)));
  }

  @Override
  public PageableResult<List<ReservationListItem>> getReservations(
      ReservationsFilterParams filterParams, PageableParam pageable) {
    ReservationsV rv = ReservationsV.RESERVATIONS_V;

    RecordMapper<Record, ReservationListItem> reservationDtoMapper =
        r ->
            new ReservationListItem(
                r.get(rv.RESERVATION_ID),
                r.get(rv.GUEST_FIRST_NAME),
                r.get(rv.GUEST_LAST_NAME),
                r.get(rv.START_DATE),
                r.get(rv.END_DATE),
                r.get(rv.CREATED_AT),
                r.get(rv.UPDATED_AT),
                ChronoUnit.DAYS.between(r.get(rv.START_DATE), r.get(rv.END_DATE)),
                DictionaryValue.of(r, rv.STATUS_CODE, rv.STATUS_NAME),
                DictionaryValue.of(r, rv.SOURCE_CODE, rv.SOURCE_NAME),
                r.get(rv.TOTAL_PRICE),
                r.get(rv.ROOMS_QUANTITY));

    Select<?> select = dsl.selectFrom(rv).where(buildReservationsCondition(filterParams));

    Function<Table<?>, SortField<?>[]> sortFieldProvider =
        table ->
            SortFieldProvider.builder()
                .table(table)
                .pageable(pageable)
                .defaultField(rv.CREATED_AT, LocalDateTime.class)
                .defaultDesc()
                .build();

    return PaginatedQueryBuilder.<ReservationListItem>builder()
        .dsl(dsl)
        .baseSelect(select)
        .pageable(pageable)
        .sortFieldProvider(sortFieldProvider)
        .mapper(reservationDtoMapper)
        .build()
        .fetch();
  }

  @Override
  public PageableResult<List<ReservationListItem>> getMyReservations(
      UUID appUserId, ReservationsFilterParams filterParams, PageableParam pageable) {
    ReservationsV rv = ReservationsV.RESERVATIONS_V;

    RecordMapper<Record, ReservationListItem> reservationDtoMapper =
        r ->
            new ReservationListItem(
                r.get(rv.RESERVATION_ID),
                null,
                null,
                r.get(rv.START_DATE),
                r.get(rv.END_DATE),
                r.get(rv.CREATED_AT),
                r.get(rv.UPDATED_AT),
                ChronoUnit.DAYS.between(r.get(rv.START_DATE), r.get(rv.END_DATE)),
                DictionaryValue.of(r, rv.STATUS_CODE, rv.STATUS_NAME),
                DictionaryValue.of(r, rv.SOURCE_CODE, rv.SOURCE_NAME),
                r.get(rv.TOTAL_PRICE),
                r.get(rv.ROOMS_QUANTITY));

    Select<?> select =
        dsl.selectFrom(rv)
            .where(rv.APP_USER_ID.eq(appUserId))
            .and(buildReservationsCondition(filterParams));

    Function<Table<?>, SortField<?>[]> sortFieldProvider =
        table ->
            SortFieldProvider.builder()
                .table(table)
                .pageable(pageable)
                .defaultField(rv.CREATED_AT, LocalDateTime.class)
                .defaultDesc()
                .build();

    return PaginatedQueryBuilder.<ReservationListItem>builder()
        .dsl(dsl)
        .baseSelect(select)
        .pageable(pageable)
        .sortFieldProvider(sortFieldProvider)
        .mapper(reservationDtoMapper)
        .build()
        .fetch();
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
        .where(v.RESERVATION_STATUS_CODE.ne(RESERVATION_CANCELLED_STATUS_CODE))
        .and(v.START_DATE.le(endDate).and(v.END_DATE.ge(startDate)))
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

  private List<RoomDto> getReservationRooms(int reservationId) {
    RoomV v = RoomV.ROOM_V;
    ReservationRoom rr = ReservationRoom.RESERVATION_ROOM;
    List<Integer> roomIds =
        dsl.select(rr.ROOM_ID)
            .from(rr)
            .where(rr.RESERVATION_ID.eq(reservationId))
            .fetch(rr.ROOM_ID);
    return dsl.selectFrom(v)
        .where(v.ROOM_ID.in(roomIds))
        .fetch(
            r ->
                new RoomDto(
                    r.get(v.ROOM_ID),
                    r.get(v.ROOM_NUMBER),
                    new DictionaryValue(r.get(v.STANDARD_CODE), r.get(v.STANDARD_NAME)),
                    r.get(v.CAPACITY),
                    r.get(v.PRICE_PER_NIGHT),
                    r.get(v.FLOOR),
                    r.get(v.AREA_M2)));
  }

  private Condition buildReservationsCondition(ReservationsFilterParams filterParams) {
    Condition condition = DSL.trueCondition();
    if (filterParams == null) {
      return condition;
    }

    ReservationsV rv = ReservationsV.RESERVATIONS_V;
    if (filterParams.query() != null) {
      String pattern = "%" + filterParams.query().trim() + "%";
      condition =
          condition.and(
              DSL.concat(rv.GUEST_FIRST_NAME, DSL.inline(" "), rv.GUEST_LAST_NAME)
                  .likeIgnoreCase(pattern));
    }
    if (filterParams.reservationStatusCode() != null) {
      condition = condition.and(rv.STATUS_CODE.eq(filterParams.reservationStatusCode()));
    }
    if (filterParams.from() != null) {
      condition = condition.and(rv.END_DATE.ge(filterParams.from()));
    }
    if (filterParams.to() != null) {
      condition = condition.and(rv.START_DATE.le(filterParams.to()));
    }
    return condition;
  }
}
