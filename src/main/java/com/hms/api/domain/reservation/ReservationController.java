package com.hms.api.domain.reservation;

import com.hms.api.common.dto.LabeledValue;
import com.hms.api.domain.reservation.dto.*;
import com.hms.api.domain.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @GetMapping("/{reservation-id}")
  public ResponseEntity<ReservationDetails> getReservation(
      @PathVariable("reservation-id") int reservationId) {
    return ResponseEntity.ok(reservationService.getReservation(reservationId));
  }

  @GetMapping()
  public ResponseEntity<List<ReservationDto>> getMyReservations() {
    return ResponseEntity.ok(reservationService.getMyReservations());
  }

  @GetMapping("/all")
  public ResponseEntity<List<NamedReservationDto>> getAllReservations() {
    return ResponseEntity.ok(reservationService.getAllReservations());
  }

  @PostMapping()
  public ResponseEntity<LabeledValue<Integer>> makeReservation(
      @RequestBody MakeReservationRequest request) {
    int reservationId = reservationService.makeReservation(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LabeledValue<>("reservationId", reservationId));
  }

  @PostMapping("/search")
  public ResponseEntity<List<ReservationOffer>> getReservationOffers(
      @RequestBody SearchReservationOffersRequest request) {
    return ResponseEntity.ok(reservationService.getReservationOffers(request));
  }

  @PatchMapping("/{reservationId}/status")
  public ResponseEntity<Void> updateReservationStatus(
      @PathVariable int reservationId, @RequestBody UpdateReservationStatusRequest request) {
    reservationService.updateReservationStatus(reservationId, request);
    return ResponseEntity.noContent().build();
  }
}
