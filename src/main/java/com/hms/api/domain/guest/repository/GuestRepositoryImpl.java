package com.hms.api.domain.guest.repository;

import com.hms.api.common.dictionary.dto.DictionaryValue;
import com.hms.api.domain.guest.dto.ReservationRoomDto;
import com.hms.generated.jooq.hms.Tables;
import com.hms.generated.jooq.hms.tables.TypeCitizenship;
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
      record.setDocumentTypeCode(quest.documentType().getCode());
      record.setDocumentNumber(quest.documentNumber());
      record.setCitizenshipCode(quest.citizenshipCode());
      record.setPhone(quest.phone());
      questRecords.add(record);
    }
    dsl.batchInsert(questRecords).execute();
  }

  @Override
  public List<DictionaryValue> getDocumentTypes() {
    TypeCitizenship tc = TypeCitizenship.TYPE_CITIZENSHIP;
    return dsl.selectFrom(tc)
        .where(tc.IS_ACTIVE.isTrue())
        .fetch(r -> new DictionaryValue(r.getCode(), r.getName()));
  }

  @Override
  public List<DictionaryValue> getCitizenshipList() {
    TypeCitizenship tc = TypeCitizenship.TYPE_CITIZENSHIP;
    return dsl.selectFrom(tc)
        .where(tc.IS_ACTIVE.isTrue())
        .fetch(r -> new DictionaryValue(r.getCode(), r.getName()));
  }
}
