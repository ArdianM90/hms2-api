package com.hms.api.domain.reservation.service;

import com.hms.api.domain.reservation.dto.ReservationOffer;
import com.hms.api.domain.reservation.dto.SearchReservationOffersRequest;
import java.util.List;

public interface ReservationService {

  List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request);
}
