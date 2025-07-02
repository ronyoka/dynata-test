package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.HEADER_NAME;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_STATUS_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

@DisplayName("Status Mapper Tests")
class StatusMapperTest extends BaseMapperTest {

    private static final String COMPLETED_NAME = "Completed";
    private static final int COMPLETED_ID = 1;
    private StatusMapper statusMapper;
    private CSVRecord validRecord;

    @BeforeEach
    void setUp() throws IOException {
        statusMapper = Mappers.getMapper(StatusMapper.class);
        validRecord = createCsvRecord(RecordParams.builder()
                                                  .statusId(String.valueOf(COMPLETED_ID))
                                                  .name(COMPLETED_NAME)
                                                  .build());
    }

    private CSVRecord createCsvRecord(RecordParams recordParams) throws IOException {
        String csvData = "%s,%s\n%s,%s".formatted(
                HEADER_STATUS_ID,
                HEADER_NAME,
                recordParams.getStatusId(),
                recordParams.getName());
        return parseFirstCsvRecord(csvData);
    }

    @Test
    @DisplayName("Should map all fields correctly")
    void shouldMapAllFields() {
        var status = statusMapper.toStatus(validRecord);

        assertNotNull(status);
        assertEquals(COMPLETED_ID, status.getId());
        assertEquals(COMPLETED_NAME, status.getName());
    }

    @Test
    @DisplayName("Should return ID when ID is valid")
    void shouldReturnIdWhenIdIsValid() {
        assertEquals(COMPLETED_ID, statusMapper.toStatusId(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when ID is empty")
    void shouldThrowExceptionWhenIdIsEmpty() throws IOException {
        var recordWithEmptyId = createCsvRecord(RecordParams.builder()
                                                            .statusId("")
                                                            .name(COMPLETED_NAME)
                                                            .build());

        var exception = assertThrows(IllegalStateException.class, () -> statusMapper.toStatusId(recordWithEmptyId));

        assertTrue(exception.getMessage().contains("Status Id cannot be empty"));
    }

    @Test
    @DisplayName("Should return name when name is valid")
    void shouldReturnNameWhenNameIsValid() {
        assertEquals(COMPLETED_NAME, statusMapper.toName(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() throws IOException {
        var recordWithEmptyName = createCsvRecord(RecordParams.builder()
                                                              .statusId(String.valueOf(COMPLETED_ID))
                                                              .name("")
                                                              .build());

        var exception = assertThrows(IllegalStateException.class, () -> statusMapper.toName(recordWithEmptyName));

        assertTrue(exception.getMessage().contains("Status name cannot be empty"));
    }

    @Test
    @DisplayName("Should throw exception when ID is invalid")
    void shouldThrowExceptionWhenIdIsInvalid() throws IOException {
        var recordWithInvalidId = createCsvRecord(RecordParams.builder()
                                                              .statusId("not-an-id")
                                                              .name(COMPLETED_NAME)
                                                              .build());

        assertThrows(NumberFormatException.class, () -> statusMapper.toStatus(recordWithInvalidId));
    }

    @Builder
    @Getter
    @AllArgsConstructor
    private static final class RecordParams {
        private final String statusId;
        private final String name;
    }
}
