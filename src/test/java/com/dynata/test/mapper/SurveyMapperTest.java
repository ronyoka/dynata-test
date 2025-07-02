package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.HEADER_COMPLETION_POINTS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_EXPECTED_COMPLETES;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_FILTERED_POINTS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_NAME;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_SURVEY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@DisplayName("Survey Mapper Tests")
class SurveyMapperTest extends BaseMapperTest {

    private static final int SURVEY_ID_VALUE = 101;
    private static final int FILTERED_POINTS = 50;
    private static final int EXPECTED_COMPLETES = 500;
    private static final int COMPLETION_POINTS = 100;
    private static final String SURVEY_NAME = "Test Survey";

    private SurveyMapper surveyMapper;
    private CSVRecord validRecord;

    @BeforeEach
    void setUp() throws IOException {
        surveyMapper = Mappers.getMapper(SurveyMapper.class);
        validRecord = createCsvRecord(RecordParams.builder()
                                                  .surveyId(String.valueOf(SURVEY_ID_VALUE))
                                                  .name(SURVEY_NAME)
                                                  .expectedCompletes(String.valueOf(EXPECTED_COMPLETES))
                                                  .completionPoints(String.valueOf(COMPLETION_POINTS))
                                                  .filteredPoints(String.valueOf(FILTERED_POINTS))
                                                  .build());
    }

    private CSVRecord createCsvRecord(RecordParams recordParams) throws IOException {
        String csvData = "%s,%s,%s,%s,%s\n%s,%s,%s,%s,%s".formatted(
                HEADER_SURVEY_ID,
                HEADER_NAME,
                HEADER_EXPECTED_COMPLETES,
                HEADER_COMPLETION_POINTS,
                HEADER_FILTERED_POINTS,
                recordParams.getSurveyId(),
                recordParams.getName(),
                recordParams.getExpectedCompletes(),
                recordParams.getCompletionPoints(),
                recordParams.getFilteredPoints());
        return parseFirstCsvRecord(csvData);
    }

    @Test
    @DisplayName("Should map all fields correctly")
    void shouldMapAllFields() {
        var survey = surveyMapper.toSurvey(validRecord);

        assertNotNull(survey);
        assertEquals(SURVEY_ID_VALUE, survey.getId());
        assertEquals(SURVEY_NAME, survey.getName());
        assertEquals(EXPECTED_COMPLETES, survey.getExpectedCompletes());
        assertEquals(COMPLETION_POINTS, survey.getCompletionPoints());
        assertEquals(FILTERED_POINTS, survey.getFilteredPoints());
    }

    @Test
    @DisplayName("Should return ID when ID is valid")
    void shouldReturnIdWhenIdIsValid() {
        assertEquals(SURVEY_ID_VALUE, surveyMapper.toSurveyId(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when ID is empty")
    void shouldThrowExceptionWhenIdIsEmpty() throws IOException {
        var recordWithEmptyId = createCsvRecord(RecordParams.builder()
                                                            .surveyId(StringUtils.EMPTY)
                                                            .name(SURVEY_NAME)
                                                            .expectedCompletes(String.valueOf(EXPECTED_COMPLETES))
                                                            .completionPoints(String.valueOf(COMPLETION_POINTS))
                                                            .filteredPoints(String.valueOf(FILTERED_POINTS))
                                                            .build());

        var exception = assertThrows(IllegalStateException.class, () -> surveyMapper.toSurveyId(recordWithEmptyId));

        assertTrue(exception.getMessage().contains("Survey Id cannot be empty"));
    }

    @Test
    @DisplayName("Should return name when name is valid")
    void shouldReturnNameWhenNameIsValid() {
        assertEquals(SURVEY_NAME, surveyMapper.toName(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    void shouldThrowExceptionWhenNameIsEmpty() throws IOException {
        var recordWithEmptyName = createCsvRecord(RecordParams.builder()
                                                              .surveyId(String.valueOf(SURVEY_ID_VALUE))
                                                              .name("")
                                                              .expectedCompletes(String.valueOf(EXPECTED_COMPLETES))
                                                              .completionPoints(String.valueOf(COMPLETION_POINTS))
                                                              .filteredPoints(String.valueOf(FILTERED_POINTS))
                                                              .build());

        var exception = assertThrows(IllegalStateException.class,
                                     () -> surveyMapper.toName(recordWithEmptyName));

        assertTrue(exception.getMessage().contains("Survey name cannot be empty"));
    }

    @Test
    @DisplayName("Should return expected completes when valid")
    void shouldReturnExpectedCompletesWhenValid() {
        assertEquals(EXPECTED_COMPLETES, surveyMapper.toExpectedCompletes(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when expected completes is empty")
    void shouldThrowExceptionWhenExpectedCompletesEmpty() throws IOException {
        var recordWithEmptyExpectedCompletes = createCsvRecord(RecordParams.builder()
                                                                           .surveyId(String.valueOf(SURVEY_ID_VALUE))
                                                                           .name(SURVEY_NAME)
                                                                           .expectedCompletes("")
                                                                           .completionPoints(String.valueOf(COMPLETION_POINTS))
                                                                           .filteredPoints(String.valueOf(FILTERED_POINTS))
                                                                           .build());

        var exception = assertThrows(IllegalStateException.class,
                                     () -> surveyMapper.toExpectedCompletes(recordWithEmptyExpectedCompletes));

        assertTrue(exception.getMessage().contains("Expected completes cannot be empty"));
    }

    @Test
    @DisplayName("Should return completion points when valid")
    void shouldReturnCompletionPointsWhenValid() {
        assertEquals(COMPLETION_POINTS, surveyMapper.toCompletionPoints(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when completion points is empty")
    void shouldThrowExceptionWhenCompletionPointsEmpty() throws IOException {
        var recordWithEmptyCompletionPoints = createCsvRecord(RecordParams.builder()
                                                                          .surveyId(String.valueOf(SURVEY_ID_VALUE))
                                                                          .name(SURVEY_NAME)
                                                                          .expectedCompletes(String.valueOf(EXPECTED_COMPLETES))
                                                                          .completionPoints("")
                                                                          .filteredPoints(String.valueOf(FILTERED_POINTS))
                                                                          .build());

        var exception = assertThrows(IllegalStateException.class, () -> surveyMapper.toCompletionPoints(recordWithEmptyCompletionPoints));

        assertTrue(exception.getMessage().contains("Completion points cannot be empty"));
    }

    @Test
    @DisplayName("Should return filtered points when valid")
    void shouldReturnFilteredPointsWhenValid() {
        assertEquals(FILTERED_POINTS, surveyMapper.toFilteredPoints(validRecord));
    }

    @Test
    @DisplayName("Should throw exception when filtered points is empty")
    void shouldThrowExceptionWhenFilteredPointsEmpty() throws IOException {
        var recordWithEmptyFilteredPoints = createCsvRecord(RecordParams.builder()
                                                                        .surveyId(String.valueOf(SURVEY_ID_VALUE))
                                                                        .name(SURVEY_NAME)
                                                                        .expectedCompletes(String.valueOf(EXPECTED_COMPLETES))
                                                                        .completionPoints(String.valueOf(COMPLETION_POINTS))
                                                                        .filteredPoints("")
                                                                        .build());

        var exception = assertThrows(IllegalStateException.class, () -> surveyMapper.toFilteredPoints(recordWithEmptyFilteredPoints));

        assertTrue(exception.getMessage().contains("Filtered points cannot be empty"));
    }

    @Builder
    @Getter
    @AllArgsConstructor
    private static final class RecordParams {
        private final String surveyId;
        private final String name;
        private final String expectedCompletes;
        private final String completionPoints;
        private final String filteredPoints;
    }
}
