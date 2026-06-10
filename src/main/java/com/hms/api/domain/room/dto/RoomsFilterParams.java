package com.hms.api.domain.room.dto;

public record RoomsFilterParams(
    Integer[] roomCapacities, String standardCode, Integer priceFrom, Integer priceTo) {}
