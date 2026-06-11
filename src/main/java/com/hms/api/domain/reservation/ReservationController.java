package com.hms.api.domain.reservation;

import com.hms.api.common.dto.IntIdResponse;
import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationOffer;
import com.hms.api.domain.reservation.dto.SearchReservationOffersRequest;
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

  @PostMapping()
  public ResponseEntity<IntIdResponse> makeReservation(
      @AuthenticationPrincipal Jwt jwt, @RequestBody MakeReservationRequest request) {
    reservationService.makeReservation(jwt, request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/search")
  public ResponseEntity<List<ReservationOffer>> getReservationOffers(
      @RequestBody SearchReservationOffersRequest request) {
    return ResponseEntity.ok(reservationService.getReservationOffers(request));
  }
}
