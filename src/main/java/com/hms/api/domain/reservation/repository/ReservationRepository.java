package com.hms.api.domain.reservation.repository;

import com.hms.api.domain.reservation.dto.ReservationDto;
import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {

  List<ReservationDto> getReservations(LocalDate startDate, LocalDate endDate);
}
