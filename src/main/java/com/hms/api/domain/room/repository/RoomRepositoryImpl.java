package com.hms.api.domain.room.repository;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.domain.room.dto.*;
import com.hms.generated.jooq.hms.Tables;
import com.hms.generated.jooq.hms.tables.RoomV;
import com.hms.generated.jooq.hms.tables.records.RoomRecord;
import com.hms.generated.jooq.hms.tables.records.RoomVRecord;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

  private final DSLContext dsl;

  @Override
  public RoomDto getRoom(int roomId) {
    RoomV v = RoomV.ROOM_V;
    return dsl.selectFrom(v).where(v.ROOM_ID.eq(roomId)).fetchOne(this::mapToRoomDto);
  }

  @Override
  public List<RoomDto> getRooms(RoomsFilterParams filterParams) {
    RoomV v = RoomV.ROOM_V;
    Condition condition = DSL.trueCondition();
    if (filterParams.roomCapacities() != null) {
      condition = condition.and(v.CAPACITY.in(filterParams.roomCapacities()));
    }
    if (filterParams.standardCode() != null) {
      condition = condition.and(v.STANDARD_CODE.eq(filterParams.standardCode()));
    }
    if (filterParams.priceFrom() != null) {
      condition = condition.and(v.PRICE_PER_NIGHT.ge(BigDecimal.valueOf(filterParams.priceFrom())));
    }
    if (filterParams.priceTo() != null) {
      condition = condition.and(v.PRICE_PER_NIGHT.le(BigDecimal.valueOf(filterParams.priceTo())));
    }
    return dsl.selectFrom(v).where(condition).fetch(this::mapToRoomDto);
  }

  @Override
  public List<RoomSimpleDto> getRoomsSimple() {
    RoomV v = RoomV.ROOM_V;
    return dsl.select(v.ROOM_ID, v.ROOM_NUMBER).from(v).fetchInto(RoomSimpleDto.class);
  }

  @Override
  public int createRoom(CreateRoomRequest request) {
    RoomRecord room = dsl.newRecord(Tables.ROOM);
    room.setCapacity(request.capacity());
    room.setPricePerNight(request.pricePerNight());
    room.setRoomNumber(request.roomNumber());
    room.setFloor(request.floor());
    room.setAreaM2(request.areaM2());
    room.setTypeRoomStandardCode(request.roomStandardCode());
    room.store();
    return room.getRoomId();
  }

  @Override
  public void updateRoom(int roomId, UpdateRoomRequest request) {
    dsl.update(Tables.ROOM)
        .set(Tables.ROOM.ROOM_NUMBER, request.roomNumber())
        .set(Tables.ROOM.TYPE_ROOM_STANDARD_CODE, request.roomStandardCode())
        .set(Tables.ROOM.CAPACITY, request.capacity())
        .set(Tables.ROOM.PRICE_PER_NIGHT, request.pricePerNight())
        .set(Tables.ROOM.FLOOR, request.floor())
        .set(Tables.ROOM.AREA_M2, request.areaM2())
        .set(Tables.ROOM.UPDATED_AT, LocalDateTime.now())
        .where(Tables.ROOM.ROOM_ID.eq(roomId))
        .execute();
  }

  @Override
  public void deleteRoom(int roomId) {
    dsl.deleteFrom(Tables.ROOM).where(Tables.ROOM.ROOM_ID.eq(roomId)).execute();
  }

  private RoomDto mapToRoomDto(RoomVRecord r) {
    RoomV v = RoomV.ROOM_V;
    return new RoomDto(
        r.get(v.ROOM_ID),
        r.get(v.ROOM_NUMBER),
        new DictionaryValue(r.get(v.STANDARD_CODE), r.get(v.STANDARD_NAME)),
        r.get(v.CAPACITY),
        r.get(v.PRICE_PER_NIGHT),
        r.get(v.FLOOR),
        r.get(v.AREA_M2));
  }
}
