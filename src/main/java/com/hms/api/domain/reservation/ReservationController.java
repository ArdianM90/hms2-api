package com.hms.api.domain.reservation;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.domain.reservation.dto.*;
import com.hms.api.domain.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping()
  public ResponseEntity<List<ReservationDto>> getMyReservations(@AuthenticationPrincipal Jwt jwt) {
    return ResponseEntity.ok(reservationService.getMyReservations(jwt));
  }

  @PostMapping()
  public ResponseEntity<LabeledValue<Integer>> makeReservation(
      @AuthenticationPrincipal Jwt jwt, @RequestBody MakeReservationRequest request) {
    int reservationId = reservationService.makeReservation(jwt, request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("reservationId", reservationId));
  }

  @PostMapping("/search")
  public ResponseEntity<List<ReservationOffer>> getReservationOffers(
      @RequestBody SearchReservationOffersRequest request) {
    return ResponseEntity.ok(reservationService.getReservationOffers(request));
  }

  @PatchMapping("/{reservationId}/status")
  public ResponseEntity<List<ReservationOffer>> updateReservationStatus(
      @PathVariable int reservationId, @RequestBody UpdateReservationStatusRequest request) {
    reservationService.updateReservationStatus(reservationId, request);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
