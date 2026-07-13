package com.hms.api.domain.reservation.repository;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.domain.reservation.dto.*;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository {

  ReservationDetails getReservation(int reservationId);

  PageableResult<List<ReservationListItem>> getReservations(
      ReservationsFilterParams filterParams, PageableParam pageable);

  PageableResult<List<ReservationListItem>> getMyReservations(
      UUID appUserId, ReservationsFilterParams filterParams, PageableParam pageable);

  @Transactional
  int makeReservation(UUID appUserId, ReservationSource source, MakeReservationRequest request);

  Set<Integer> getReservedRoomIds(LocalDate startDate, LocalDate endDate);

  void updateReservationStatus(int reservationId, ReservationStatus status);
}
