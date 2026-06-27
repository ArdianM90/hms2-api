package com.hms.api.domain.guest.repository;

import com.hms.api.domain.guest.dto.ReservationRoomDto;
import com.hms.generated.jooq.hms.Tables;
import com.hms.generated.jooq.hms.tables.records.ReservationGuestRecord;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GuestRepositoryImpl implements GuestRepository {

  private final DSLContext dsl;

  @Override
  public void checkInGuests(List<ReservationRoomDto> quests) {
    List<ReservationGuestRecord> questRecords = new ArrayList<>();
    for (ReservationRoomDto quest : quests) {
      ReservationGuestRecord record = dsl.newRecord(Tables.RESERVATION_GUEST);
      record.setReservationId(quest.reservationId());
      record.setRoomId(quest.roomId());
      record.setFirstName(quest.firstName());
      record.setLastName(quest.lastName());
      record.setPesel(quest.pesel());
      record.setDateOfBirth(quest.dateOfBirth());
      record.setDocumentTypeCode(quest.documentTypeCode());
      record.setDocumentNumber(quest.documentNumber());
      record.setCitizenshipCode(quest.citizenshipCode());
      record.setPhone(quest.phone());
      questRecords.add(record);
    }
    dsl.batchInsert(questRecords).execute();
  }
}
