package com.hms.api.domain.reservation.service;

import com.hms.api.domain.reservation.dto.ReservationDto;
import com.hms.api.domain.reservation.dto.ReservationOffer;
import com.hms.api.domain.reservation.dto.RoomOffer;
import com.hms.api.domain.reservation.dto.SearchReservationOffersRequest;
import com.hms.api.domain.reservation.exception.TooManyRoomsException;
import com.hms.api.domain.reservation.repository.ReservationRepository;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomsFilterParams;
import com.hms.api.domain.room.service.RoomService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final RoomService roomService;

  public List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request) {
    validateSearchReservationRequest(request);

    Set<Integer> reservedRoomIds =
        reservationRepository.getReservations(request.startDate(), request.endDate()).stream()
            .map(ReservationDto::roomId)
            .collect(Collectors.toSet());
    List<RoomDto> availableRooms =
        roomService
            .getRooms(
                new RoomsFilterParams(
                    null, request.standardCode(), request.priceFrom(), request.priceTo()))
            .stream()
            .filter(r -> !reservedRoomIds.contains(r.roomId()))
            .toList();
    List<List<RoomDto>> candidatesPerSlot =
        Arrays.stream(request.roomCapacities())
            .map(
                requiredCapacity ->
                    availableRooms.stream().filter(r -> r.capacity() >= requiredCapacity).toList())
            .toList();
    List<List<RoomDto>> combinations = cartesianProduct(candidatesPerSlot);

    return combinations.stream()
        .filter(
            combo -> {
              Set<Integer> ids = combo.stream().map(RoomDto::roomId).collect(Collectors.toSet());
              return ids.size() == combo.size();
            })
        .distinct()
        .map(
            combo -> {
              List<RoomOffer> roomOffers =
                  combo.stream()
                      .map(r -> new RoomOffer(r.standard(), r.capacity(), r.pricePerNight()))
                      .toList();
              BigDecimal totalPrice =
                  roomOffers.stream()
                      .map(RoomOffer::pricePerNight)
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
              return new ReservationOffer(totalPrice, roomOffers);
            })
        .toList();
  }

  private List<List<RoomDto>> cartesianProduct(List<List<RoomDto>> lists) {
    List<List<RoomDto>> result = new ArrayList<>();
    result.add(new ArrayList<>());

    for (List<RoomDto> candidates : lists) {
      List<List<RoomDto>> newResult = new ArrayList<>();
      for (List<RoomDto> existing : result) {
        for (RoomDto candidate : candidates) {
          List<RoomDto> newCombo = new ArrayList<>(existing);
          newCombo.add(candidate);
          newResult.add(newCombo);
        }
      }
      result = newResult;
    }

    return result;
  }

  private void validateSearchReservationRequest(SearchReservationOffersRequest request) {
    int roomsQty = roomService.getRoomsQuantity().quantity();
    if (request.roomCapacities().length > roomsQty) {
      throw new TooManyRoomsException("Przekroczono liczbę dostępnych pokojów.");
    }
  }
}
