package com.hms.api.domain.guest.service;

import com.hms.api.common.exception.BusinessException;
import com.hms.api.domain.guest.dto.CheckInRequest;
import com.hms.api.domain.guest.dto.GuestCheckInRequest;
import com.hms.api.domain.guest.dto.ReservationRoomDto;
import com.hms.api.domain.guest.dto.RoomCheckInRequest;
import com.hms.api.domain.guest.model.DocumentType;
import com.hms.api.domain.guest.repository.GuestRepository;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

  private final GuestRepository guestRepository;

  @Override
  public void checkInGuests(CheckInRequest request) {
    List<ReservationRoomDto> quests = mapRequestToGuestDtoList(request);
    guestRepository.checkInGuests(quests);
  }

  private List<ReservationRoomDto> mapRequestToGuestDtoList(CheckInRequest request) {
    List<ReservationRoomDto> result = new ArrayList<>();
    int reservationId = request.reservationId();
    for (RoomCheckInRequest room : request.rooms()) {
      int roomId = room.roomId();
      for (GuestCheckInRequest guest : room.guests()) {
        if (guest.documentType() != DocumentType.OTHER
            && StringUtils.isBlank(guest.documentNumber())) {
          throw new BusinessException("Numer dokumentu jest wymagany.");
        }
        result.add(
            new ReservationRoomDto(
                reservationId,
                roomId,
                guest.firstName(),
                guest.lastName(),
                guest.pesel(),
                guest.dateOfBirth(),
                guest.documentType(),
                guest.documentNumber(),
                guest.citizenshipCode(),
                guest.phone()));
      }
    }
    return result;
  }
}
