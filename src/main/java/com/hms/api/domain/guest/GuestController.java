package com.hms.api.domain.guest;

import com.hms.api.domain.guest.dto.CheckInRequest;
import com.hms.api.domain.guest.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation-guest")
@RequiredArgsConstructor
public class GuestController {

  private final GuestService guestService;

  @PostMapping()
  public ResponseEntity<Void> checkInGuests(@RequestBody @Valid CheckInRequest request) {
    guestService.checkInGuests(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
