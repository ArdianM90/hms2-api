package com.hms.api.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl {

  private final DSLContext dsl;
}
