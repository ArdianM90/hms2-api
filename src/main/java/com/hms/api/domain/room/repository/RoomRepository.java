package com.hms.api.domain.room.repository;

import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.api.domain.room.dto.UpdateRoomRequest;
import java.util.List;

public interface RoomRepository {

  RoomDto getRoom(int id);

  List<RoomDto> getRooms();

  int createRoom(CreateRoomRequest request);

  void updateRoom(int id, UpdateRoomRequest request);

  void deleteRoom(int id);

  List<RoomStandard> getRoomStandards();
}
