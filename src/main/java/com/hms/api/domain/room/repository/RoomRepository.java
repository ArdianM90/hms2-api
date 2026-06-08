package com.hms.api.domain.room.repository;

import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import java.util.List;

public interface RoomRepository {

  List<RoomDto> getRooms();

  int createRoom(CreateRoomRequest request);

  void deleteRoom(int id);

  List<RoomStandard> getRoomStandards();
}
