package com.hms.api.domain.room.repository;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.domain.room.dto.*;
import java.util.List;

public interface RoomRepository {

  RoomDto getRoom(int roomId);

  List<RoomDto> getRooms(RoomsFilterParams filterParams);

  int createRoom(CreateRoomRequest request);

  void updateRoom(int roomId, UpdateRoomRequest request);

  void deleteRoom(int roomId);

  List<DictionaryValue> getRoomStandards();
}
