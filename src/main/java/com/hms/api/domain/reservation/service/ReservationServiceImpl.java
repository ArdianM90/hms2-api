package com.hms.api.domain.reservation.service;

import com.hms.api.common.dto.PageableParam;
import com.hms.api.common.dto.PageableResult;
import com.hms.api.common.security.AuthContext;
import com.hms.api.domain.reservation.dto.*;
import com.hms.api.domain.reservation.exception.TooManyRoomsException;
import com.hms.api.domain.reservation.model.ReservationSource;
import com.hms.api.domain.reservation.repository.ReservationRepository;
import com.hms.api.domain.room.dto.RoomDto;
import com.hms.api.domain.room.dto.RoomsFilterParams;
import com.hms.api.domain.room.service.RoomService;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final AuthContext authContext;
  private final RoomService roomService;
  private final ReservationRepository reservationRepository;

  @Override
  public ReservationDetails getReservation(int reservationId) {
    return reservationRepository.getReservation(reservationId);
  }

  @Override
  public PageableResult<List<ReservationListItem>> getReservations(
      ReservationsFilterParams filterParams, PageableParam pageable) {
    if (authContext.isAdmin()) {
      return reservationRepository.getReservations(filterParams, pageable);
    }
    UUID appUserId = authContext.currentUserId();
    return reservationRepository.getMyReservations(appUserId, filterParams, pageable);
  }

  @Override
  public int makeReservation(MakeReservationRequest request) {
    UUID appUserId = authContext.currentUserId();
    ReservationSource source = authContext.requestSource();
    return reservationRepository.makeReservation(appUserId, source, request);
  }

  @Override
  public List<ReservationOffer> getReservationOffers(SearchReservationOffersRequest request) {
    validateSearchReservationRequest(request);
    List<RoomDto> availableRooms = getAvailableRooms(request);
    List<List<RoomDto>> combinations = buildCombinations(availableRooms, request.roomCapacities());
    return toUniqueOffers(combinations, request);
  }

  @Override
  public void updateReservationStatus(int reservationId, UpdateReservationStatusRequest request) {
    reservationRepository.updateReservationStatus(reservationId, request.statusCode());
  }

  private List<RoomDto> getAvailableRooms(SearchReservationOffersRequest request) {
    Set<Integer> reservedRoomIds =
        reservationRepository.getReservedRoomIds(request.startDate(), request.endDate());
    return roomService
        .getRooms(
            new RoomsFilterParams(
                null, request.standardCode(), request.priceFrom(), request.priceTo()))
        .stream()
        .filter(r -> !reservedRoomIds.contains(r.roomId()))
        .toList();
  }

  private List<List<RoomDto>> buildCombinations(
      List<RoomDto> availableRooms, Integer[] roomCapacities) {
    List<List<RoomDto>> candidatesPerSlot =
        Arrays.stream(roomCapacities)
            .map(
                requiredCapacity ->
                    availableRooms.stream().filter(r -> r.capacity() >= requiredCapacity).toList())
            .toList();
    return cartesianProduct(candidatesPerSlot);
  }

  private List<ReservationOffer> toUniqueOffers(
      List<List<RoomDto>> combinations, SearchReservationOffersRequest request) {
    int numberOfNights = (int) ChronoUnit.DAYS.between(request.startDate(), request.endDate());
    Set<List<RoomOfferKey>> seen = new HashSet<>();

    return combinations.stream()
        .filter(this::hasUniqueRooms)
        .filter(combo -> isUniqueOffer(combo, seen))
        .map(combo -> toReservationOffer(combo, numberOfNights))
        .toList();
  }

  private boolean hasUniqueRooms(List<RoomDto> combo) {
    Set<Integer> ids = combo.stream().map(RoomDto::roomId).collect(Collectors.toSet());
    return ids.size() == combo.size();
  }

  private boolean isUniqueOffer(List<RoomDto> combo, Set<List<RoomOfferKey>> seen) {
    List<RoomOfferKey> key =
        combo.stream()
            .map(r -> new RoomOfferKey(r.standard(), r.capacity(), r.pricePerNight()))
            .sorted(
                Comparator.comparingInt(RoomOfferKey::capacity)
                    .thenComparing(k -> k.standard().code()))
            .toList();
    return seen.add(key);
  }

  private ReservationOffer toReservationOffer(List<RoomDto> combo, int numberOfNights) {
    List<RoomOffer> roomOffers =
        combo.stream()
            .map(r -> new RoomOffer(r.roomId(), r.standard(), r.capacity(), r.pricePerNight()))
            .toList();
    BigDecimal totalPrice =
        roomOffers.stream()
            .map(RoomOffer::pricePerNight)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .multiply(BigDecimal.valueOf(numberOfNights));
    return new ReservationOffer(numberOfNights, totalPrice, roomOffers);
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
