package com.hms.api.domain.room.repository;

import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.auth.generated.jooq.hms.Tables;
import com.hms.auth.generated.jooq.hms.tables.RoomV;
import com.hms.auth.generated.jooq.hms.tables.TypeRoomStandard;
import com.hms.auth.generated.jooq.hms.tables.records.RoomRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepository {

  private final DSLContext dsl;

  @Override
  public List<RoomDto> getRooms() {
    RoomV v = RoomV.ROOM_V;
    return dsl.selectFrom(v)
        .fetch(
            r -> {
              RoomStandard standard = new RoomStandard();
              standard.setCode(r.get(v.STANDARD_CODE));
              standard.setName(r.get(v.STANDARD_NAME));
              return new RoomDto(
                  r.get(v.ROOM_ID),
                  r.get(v.ROOM_NUMBER),
                  standard,
                  r.get(v.CAPACITY),
                  r.get(v.PRICE_PER_NIGHT),
                  r.get(v.FLOOR),
                  r.get(v.AREA_M2));
            });
  }

  @Override
  public List<RoomStandard> getRoomStandards() {
    TypeRoomStandard trs = TypeRoomStandard.TYPE_ROOM_STANDARD;
    return dsl.select(trs.CODE, trs.NAME).from(trs).fetchInto(RoomStandard.class);
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
}
