package com.hms.api.domain.room;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.common.security.RequireAdmin;
import com.hms.api.common.security.RequireGuest;
import com.hms.api.domain.room.dto.*;
import com.hms.api.domain.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@RequireGuest
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping("/{roomId}")
  public ResponseEntity<RoomDto> getRoom(@PathVariable int roomId) {
    return ResponseEntity.ok(roomService.getRoom(roomId));
  }

  @GetMapping
  public ResponseEntity<List<RoomDto>> getRooms(@ParameterObject RoomsFilterParams filterParams) {
    return ResponseEntity.ok(roomService.getRooms(filterParams));
  }

  @GetMapping("/simple")
  public ResponseEntity<List<RoomSimpleDto>> getRoomsSimple() {
    return ResponseEntity.ok(roomService.getRoomsSimple());
  }

  @GetMapping("/quantity")
  public ResponseEntity<RoomsQtyResponse> getRoomsQuantity() {
    return ResponseEntity.ok(roomService.getRoomsQuantity());
  }

  @PostMapping
  @RequireAdmin
  public ResponseEntity<LabeledValue<Integer>> createRoom(@RequestBody CreateRoomRequest request) {
    int id = roomService.createRoom(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(new LabeledValue<>("roomId", id));
  }

  @PutMapping("/{roomId}")
  @RequireAdmin
  public ResponseEntity<Void> updateRoom(
      @PathVariable int roomId, @RequestBody UpdateRoomRequest request) {
    roomService.updateRoom(roomId, request);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{roomId}")
  @RequireAdmin
  public ResponseEntity<Void> deleteRoom(@PathVariable int roomId) {
    roomService.deleteRoom(roomId);
    return ResponseEntity.noContent().build();
  }
}
