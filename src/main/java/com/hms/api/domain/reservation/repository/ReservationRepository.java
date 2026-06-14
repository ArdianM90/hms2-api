package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationDetails;
import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.model.ReservationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository {

  ReservationDetails getReservation(int reservationId);

  List<ReservationDto> getMyReservations(UUID appUserId);

  @Transactional
  int makeReservation(UUID appUserId, ReservationSource source, MakeReservationRequest request);

  Set<Integer> getReservedRoomIds(LocalDate startDate, LocalDate endDate);

  void updateReservationStatus(int reservationId, ReservationStatus status);
}
