package com.hms.api.domain.room.dto;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import java.math.BigDecimal;

public record RoomDto(
    Integer roomId,
    String roomNumber,
    DictionaryValue standard,
    Integer capacity,
    BigDecimal pricePerNight,
    Integer floor,
    Integer areaM2) {}
