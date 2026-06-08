package com.hms.api.domain.room.service;

import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.api.domain.room.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  @Override
  public List<RoomDto> getRooms() {
    return roomRepository.getRooms();
  }

  @Override
  public int createRoom(CreateRoomRequest request) {
    return roomRepository.createRoom(request);
  }

  @Override
  public void deleteRoom(int id) {
    roomRepository.deleteRoom(id);
  }

  @Override
  public List<RoomStandard> getRoomStandards() {
    return roomRepository.getRoomStandards();
  }
}
