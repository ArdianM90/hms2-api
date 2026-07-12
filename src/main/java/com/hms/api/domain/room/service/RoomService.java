package com.hms.api.domain.room.service;

import com.hms.api.domain.room.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface RoomService {

  RoomDto getRoom(int roomId);

  List<RoomDto> getRooms(RoomsFilterParams filterParams);

  List<RoomSimpleDto> getRoomsSimple();

  RoomsQtyResponse getRoomsQuantity();

  @Transactional
  int createRoom(CreateRoomRequest request);

  @Transactional
  void updateRoom(int roomId, UpdateRoomRequest request);

  @Transactional
  void deleteRoom(int roomId);
}
