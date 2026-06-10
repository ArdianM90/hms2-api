package com.hms.api.domain.reservation;

import com.hms.api.domain.reservation.dto.ReservationOffer;
import com.hms.api.domain.reservation.dto.SearchReservationOffersRequest;
import com.hms.api.domain.reservation.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService reservationService;

  @PostMapping("/search")
  public ResponseEntity<List<ReservationOffer>> getReservationOffers(
      @RequestBody SearchReservationOffersRequest request) {
    return ResponseEntity.ok(reservationService.getReservationOffers(request));
  }
}
