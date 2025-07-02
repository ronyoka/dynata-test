package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.HEADER_EMAIL_ADDRESS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_FULL_NAME;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_IS_ACTIVE;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_MEMBER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@DisplayName("Member Mapper Tests")
class MemberMapperTest extends BaseMapperTest {

    private static final int MEMBER_ID = 101;
    private static final String EMAIL = "john.doe@example.com";
    private static final String ACTIVE = "1";
    private static final String INACTIVE = "0";
    private static final String FULL_NAME = "John Doe";
    private static final String EMAIL_ERROR = "Email address not found in record";
    private static final String MEMBER_ID_ERROR = "Member Id not found in record";
    private static final String FULL_NAME_ERROR = "Full name not found in record";
    private static final String MEMBER_ID_AS_STRING = String.valueOf(MEMBER_ID);

    private MemberMapper memberMapper;
    private CSVRecord validRecord;

    @BeforeEach
    void setUp() throws IOException {
        memberMapper = Mappers.getMapper(MemberMapper.class);
        validRecord = createCsvRecord(RecordParams.builder()
                                                  .memberId(MEMBER_ID_AS_STRING)
                                                  .fullName(FULL_NAME)
                                                  .email(EMAIL)
                                                  .active(ACTIVE)
                                                  .build());
    }

    private CSVRecord createCsvRecord(RecordParams recordParams) throws IOException {
        String csvData = "%s,%s,%s,%s\n%s,%s,%s,%s".formatted(HEADER_MEMBER_ID,
                                                              HEADER_FULL_NAME,
                                                              HEADER_EMAIL_ADDRESS,
                                                              HEADER_IS_ACTIVE,
                                                              recordParams.getMemberId(),
                                                              recordParams.getFullName(),
                                                              recordParams.getEmail(),
                                                              recordParams.getActive());
        return parseFirstCsvRecord(csvData);
    }

    @Test
    @DisplayName("Should map all fields when all fields are valid")
    void toMemberShouldMapAllFieldsWhenAllFieldsAreValid() {
        var member = memberMapper.toMember(validRecord);

        assertNotNull(member);
        assertEquals(MEMBER_ID, member.getId());
        assertEquals(FULL_NAME, member.getFullname());
        assertEquals(EMAIL, member.getEmail());
        assertTrue(member.isActive());
    }

    @Test
    @DisplayName("Should return ID when ID is valid")
    void toMemberIdShouldReturnIdWhenIdIsValid() {
        assertEquals(MEMBER_ID, memberMapper.toMemberId(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when ID is empty")
    void toMemberIdShouldThrowExceptionWhenIdIsEmpty() throws IOException {
        var recordWithEmptyId = createCsvRecord(RecordParams.builder()
                                                            .memberId("")
                                                            .fullName(FULL_NAME)
                                                            .email(EMAIL)
                                                            .active(ACTIVE)
                                                            .build());

        var exception = assertThrows(IllegalStateException.class, () -> memberMapper.toMemberId(recordWithEmptyId));

        assertTrue(exception.getMessage().contains(MEMBER_ID_ERROR));
    }


    @Test
    @DisplayName("Should return full name when full name is valid")
    void toFullNameShouldReturnFullNameWhenFullNameIsValid() {
        assertEquals(FULL_NAME, memberMapper.toFullName(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when full name is empty")
    void toFullNameShouldThrowExceptionWhenFullNameIsEmpty() throws IOException {
        var recordWithEmptyFullName = createCsvRecord(RecordParams.builder()
                                                                  .memberId(MEMBER_ID_AS_STRING)
                                                                  .fullName("")
                                                                  .email(EMAIL)
                                                                  .active(ACTIVE)
                                                                  .build());

        var exception = assertThrows(IllegalStateException.class, () -> memberMapper.toFullName(recordWithEmptyFullName));

        assertTrue(exception.getMessage().contains(FULL_NAME_ERROR));
    }

    @Test
    @DisplayName("Should return email when email is valid")
    void toEmailShouldReturnEmailWhenEmailIsValid() {
        assertEquals(EMAIL, memberMapper.toEmail(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when email is empty")
    void toEmailShouldThrowExceptionWhenEmailIsEmpty() throws IOException {
        var recordWithEmptyEmail = createCsvRecord(RecordParams.builder()
                                                               .memberId(MEMBER_ID_AS_STRING)
                                                               .fullName(FULL_NAME)
                                                               .email("")
                                                               .active(ACTIVE)
                                                               .build());

        var exception = assertThrows(IllegalStateException.class, () -> memberMapper.toEmail(recordWithEmptyEmail));

        assertTrue(exception.getMessage().contains(EMAIL_ERROR));
    }

    @Test
    @DisplayName("Should return true when isActive is Y")
    void toActiveShouldReturnTrueWhenIsActiveIsY() {
        assertTrue(memberMapper.toActive(validRecord));
    }

    @Test
    @DisplayName("Should return false when isActive is not Y")
    void toActiveShouldReturnFalseWhenIsActiveIsNotY() throws IOException {
        var recordWithInactiveStatus = createCsvRecord(RecordParams.builder()
                                                                   .memberId(MEMBER_ID_AS_STRING)
                                                                   .fullName(FULL_NAME)
                                                                   .email(EMAIL)
                                                                   .active(INACTIVE)
                                                                   .build());

        assertFalse(memberMapper.toActive(recordWithInactiveStatus));
    }

    @Test
    @DisplayName("Should throw exception when isActive is empty")
    void toActiveShouldThrowExceptionWhenIsActiveIsEmpty() throws IOException {
        var recordWithEmptyIsActive = createCsvRecord(RecordParams.builder()
                                                                  .memberId(MEMBER_ID_AS_STRING)
                                                                  .fullName(FULL_NAME)
                                                                  .email(EMAIL)
                                                                  .active("")
                                                                  .build());

        var exception = assertThrows(IllegalStateException.class, () -> memberMapper.toActive(recordWithEmptyIsActive));

        assertTrue(exception.getMessage().contains(EMAIL_ERROR));
    }

    @Builder
    @Getter
    @AllArgsConstructor
    private static final class RecordParams {
        private final String memberId;
        private final String fullName;
        private final String email;
        private final String active;
    }
}
