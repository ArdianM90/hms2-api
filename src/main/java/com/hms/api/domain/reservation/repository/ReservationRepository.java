package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.MakeReservationRequest;
import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.model.ReservationSource;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository {

  @Transactional
  void makeReservation(String appUserId, ReservationSource source, MakeReservationRequest request);

  List<ReservationDto> getReservations(LocalDate startDate, LocalDate endDate);
}
