package com.hms.api.domain.room.service;

import com.hms.api.domain.room.dto.*;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface RoomService {

  RoomDto getRoom(int id);

  List<RoomDto> getRooms(RoomsFilterParams filterParams);

  @Transactional
  int createRoom(CreateRoomRequest request);

  @Transactional
  void updateRoom(int id, UpdateRoomRequest request);

  @Transactional
  void deleteRoom(int id);

  List<RoomStandard> getRoomStandards();
}
