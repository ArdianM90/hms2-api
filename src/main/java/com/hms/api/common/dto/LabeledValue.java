package com.hms.api.common.dto;

public record LabeledValue<T>(String label, T value) {}
