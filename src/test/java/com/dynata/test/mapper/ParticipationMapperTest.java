package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.HEADER_LENGTH;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_MEMBER_ID;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_STATUS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_SURVEY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.dynata.test.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Participation Mapper Tests")
class ParticipationMapperTest extends BaseMapperTest {

    private static final int MEMBER_ID = 101;
    private static final int SURVEY_ID = 201;
    private static final int DEFAULT_LENGTH = 0;
    private static final int INVALID_STATUS_ID = 2;
    private static final int STATUS_COMPLETED_ID = 1;
    private static final int PARTICIPATION_LENGTH = 30;
    private static final String STATUS_COMPLETED_NAME = "Completed";

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private SurveyMapper surveyMapper;

    @InjectMocks
    private ParticipationMapper participationMapper;

    private Map<Integer, Status> statusMap;

    @BeforeEach
    void setUp() {
        statusMap = new HashMap<>();
        statusMap.put(1, Status.builder().id(STATUS_COMPLETED_ID).name(STATUS_COMPLETED_NAME).build());

        lenient().when(memberMapper.toMemberId(any())).thenReturn(MEMBER_ID);
        lenient().when(surveyMapper.toSurveyId(any())).thenReturn(SURVEY_ID);
    }

    private CSVRecord createCsvRecord(RecordParams recordParams) throws IOException {
        return parseFirstCsvRecord("%s,%s,%s,%s\n%s,%s,%s,%s".formatted(
                HEADER_MEMBER_ID,
                HEADER_SURVEY_ID,
                HEADER_STATUS,
                HEADER_LENGTH,
                recordParams.getMemberId(),
                recordParams.getSurveyId(),
                recordParams.getStatus(),
                recordParams.getLength()));
    }

    @Test
    @DisplayName("Should map all fields correctly")
    void shouldMapAllFields() throws IOException {
        var record = createCsvRecord(RecordParams.builder()
                                                 .memberId(String.valueOf(MEMBER_ID))
                                                 .surveyId(String.valueOf(SURVEY_ID))
                                                 .status(String.valueOf(STATUS_COMPLETED_ID))
                                                 .length(String.valueOf(PARTICIPATION_LENGTH))
                                                 .build());

        var participation = participationMapper.toParticipation(record, statusMap);

        assertNotNull(participation);
        assertEquals(MEMBER_ID, participation.getMemberId());
        assertEquals(SURVEY_ID, participation.getSurveyId());
        assertEquals(STATUS_COMPLETED_NAME, participation.getStatus().getName());
        assertEquals(STATUS_COMPLETED_ID, participation.getStatus().getId());
        assertEquals(PARTICIPATION_LENGTH, participation.getLength());

        verify(memberMapper).toMemberId(record);
        verify(surveyMapper).toSurveyId(record);
    }

    @Test
    @DisplayName("Should throw exception when status is not found")
    void shouldThrowExceptionWhenStatusNotFound() throws IOException {
        var recordWithInvalidStatus = createCsvRecord(RecordParams.builder()
                                                                  .memberId(String.valueOf(MEMBER_ID))
                                                                  .surveyId(String.valueOf(SURVEY_ID))
                                                                  .status(String.valueOf(INVALID_STATUS_ID))
                                                                  .length(String.valueOf(PARTICIPATION_LENGTH))
                                                                  .build());

        var exception = assertThrows(IllegalStateException.class, () -> participationMapper.toParticipation(recordWithInvalidStatus, statusMap));

        assertTrue(exception.getMessage().contains("Status with id %d not found".formatted(INVALID_STATUS_ID)));

        verify(memberMapper).toMemberId(recordWithInvalidStatus);
        verify(surveyMapper).toSurveyId(recordWithInvalidStatus);
    }

    @Test
    @DisplayName("Should throw exception when status is empty")
    void shouldThrowExceptionWhenStatusEmpty() throws IOException {
        var recordWithEmptyStatus = createCsvRecord(RecordParams.builder()
                                                                .memberId(String.valueOf(MEMBER_ID))
                                                                .surveyId(String.valueOf(SURVEY_ID))
                                                                .status(StringUtils.EMPTY)
                                                                .length(String.valueOf(PARTICIPATION_LENGTH))
                                                                .build());

        var exception = assertThrows(IllegalStateException.class, () -> participationMapper.toParticipation(recordWithEmptyStatus, statusMap));

        assertTrue(exception.getMessage().contains("Status cannot be empty"));

        verify(memberMapper).toMemberId(recordWithEmptyStatus);
        verify(surveyMapper).toSurveyId(recordWithEmptyStatus);
    }

    @Test
    @DisplayName("Should return zero when length is empty")
    void shouldReturnZeroWhenLengthEmpty() throws IOException {
        var recordWithEmptyLength = createCsvRecord(RecordParams.builder()
                                                                .memberId(String.valueOf(MEMBER_ID))
                                                                .surveyId(String.valueOf(SURVEY_ID))
                                                                .status(String.valueOf(STATUS_COMPLETED_ID))
                                                                .length("")
                                                                .build());

        var participation = participationMapper.toParticipation(recordWithEmptyLength, statusMap);

        assertEquals(DEFAULT_LENGTH, participation.getLength());

        verify(memberMapper).toMemberId(recordWithEmptyLength);
        verify(surveyMapper).toSurveyId(recordWithEmptyLength);
    }

    @Builder
    @Getter
    @AllArgsConstructor
    private static final class RecordParams {
        private final String memberId;
        private final String surveyId;
        private final String status;
        private final String length;
    }
}
