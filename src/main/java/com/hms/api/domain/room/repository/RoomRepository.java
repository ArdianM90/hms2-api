package com.hms.api.domain.room.repository;

import com.hms.api.domain.room.dto.*;
import java.util.List;

public interface RoomRepository {

  RoomDto getRoom(int id);

  List<RoomDto> getRooms(RoomsFilterParams filterParams);

  int createRoom(CreateRoomRequest request);

  void updateRoom(int id, UpdateRoomRequest request);

  void deleteRoom(int id);

  List<RoomStandard> getRoomStandards();
}
