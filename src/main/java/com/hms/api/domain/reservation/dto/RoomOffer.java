package com.hms.api.domain.reservation.dto;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import java.math.BigDecimal;

public record RoomOffer(
    int roomId, DictionaryValue standard, Integer capacity, BigDecimal pricePerNight) {}
