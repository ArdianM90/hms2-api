package com.hms.api.domain.reservation.service;

import com.hms.api.domain.reservation.dto.*;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ReservationService {

  List<ReservationDto> getMyReservations(Jwt jwt);

  int makeReservation(Jwt jwt, MakeReservationRequest request);

  List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request);

  void updateReservationStatus(int reservationId, UpdateReservationStatusRequest request);
}
