package com.hms.api.common.auth.dto;

public record RegisterRequest(String email, String firstName, String lastName, String roleCode) {}
