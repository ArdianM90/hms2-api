package com.hms.api.domain.room;

import com.hms.api.common.dto.IntIdResponse;
import com.hms.api.domain.room.dto.CreateRoomRequest;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomStandard;
import com.hms.api.domain.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<List<RoomDto>> getRooms() {
    return ResponseEntity.ok(roomService.getRooms());
  }

  @PostMapping
  public ResponseEntity<IntIdResponse> createRoom(@RequestBody CreateRoomRequest request) {
    int id = roomService.createRoom(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new IntIdResponse(id));
  }

  @GetMapping("/standards")
  public ResponseEntity<List<RoomStandard>> getRoomStandards() {
    return ResponseEntity.ok(roomService.getRoomStandards());
  }
}
