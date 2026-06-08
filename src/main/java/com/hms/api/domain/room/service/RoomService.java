package com.hms.api.domain.room.service;

import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface RoomService {
  List<RoomDto> getRooms();

  @Transactional
  int createRoom(CreateRoomRequest request);

  @Transactional
  void deleteRoom(int id);

  List<RoomStandard> getRoomStandards();
}
