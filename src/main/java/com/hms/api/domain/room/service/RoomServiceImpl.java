package com.hms.api.domain.room.service;

import com.hms.api.domain.room.dto.*;
import com.hms.api.domain.room.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;

  @Override
  public RoomDto getRoom(int id) {
    return roomRepository.getRoom(id);
  }

  @Override
  public List<RoomDto> getRooms(RoomsFilterParams filterParams) {
    return roomRepository.getRooms(filterParams);
  }

  @Override
  public RoomsQtyResponse getRoomsQuantity() {
    return new RoomsQtyResponse(
        roomRepository.getRooms(new RoomsFilterParams(null, null, null, null)).size());
  }

  @Override
  public int createRoom(CreateRoomRequest request) {
    return roomRepository.createRoom(request);
  }

  @Override
  public void updateRoom(int id, UpdateRoomRequest request) {
    roomRepository.updateRoom(id, request);
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
