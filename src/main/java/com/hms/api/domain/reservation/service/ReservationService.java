package com.hms.api.domain.reservation.service;

import com.hms.api.domain.reservation.dto.*;
import java.util.List;

public interface ReservationService {

  ReservationDetails getReservation(int reservationId);

  List<ReservationDto> getMyReservations();

  List<NamedReservationDto> getAllReservations();

  int makeReservation(MakeReservationRequest request);

  List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request);

  void updateReservationStatus(int reservationId, UpdateReservationStatusRequest request);
}
