package com.hms.api.domain.reservation.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.reservation.dto.*;
import java.util.List;

public interface ReservationService {

  ReservationDetails getReservation(int reservationId);

  PageableResult<List<ReservationListItem>> getReservations(
      ReservationsFilterParams filterParams, PageableParam pageable);

  int makeReservation(MakeReservationRequest request);

  List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request);

  void updateReservationStatus(int reservationId, UpdateReservationStatusRequest request);
}
