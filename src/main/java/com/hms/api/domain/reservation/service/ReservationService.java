package com.hms.api.domain.reservation.service;

import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationOffer;
import com.hms.api.domain.reservation.dto.SearchReservationOffersRequest;
import java.util.List;
import org.springframework.security.oauth2.jwt.Jwt;

public interface ReservationService {

  void makeReservation(Jwt jwt, MakeReservationRequest request);

  List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request);
}
