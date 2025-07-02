package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.COMPLETION_POINTS_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.EXPECTED_COMPLETES_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.FILTERED_POINTS_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_COMPLETION_POINTS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_EXPECTED_COMPLETES;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_FILTERED_POINTS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_NAME;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_SURVEY_ID;
import static com.dynata.test.mapper.CsvColumnConstants.SURVEY_ID_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.SURVEY_NAME_EMPTY;

import com.dynata.test.model.Survey;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting CSV records to Survey objects.
 */
@Mapper(componentModel = "spring")
public interface SurveyMapper extends BaseCsvRecordMapper {

    /**
     * Maps a CSV record to a Survey object.
     *
     * @param record the CSV record
     * @return the Survey object
     */
    @Mapping(source = "record", target = "id", qualifiedByName = "toSurveyId")
    @Mapping(source = "record", target = "name", qualifiedByName = "toName")
    @Mapping(source = "record", target = "expectedCompletes", qualifiedByName = "toExpectedCompletes")
    @Mapping(source = "record", target = "completionPoints", qualifiedByName = "toCompletionPoints")
    @Mapping(source = "record", target = "filteredPoints", qualifiedByName = "toFilteredPoints")
    Survey toSurvey(CSVRecord record);

    @Named("toSurveyId")
    default int toSurveyId(CSVRecord record) {
        return getCsvColumnAsInteger(record, HEADER_SURVEY_ID).orElseThrow(() -> new IllegalStateException(SURVEY_ID_EMPTY));
    }

    @Named("toName")
    default String toName(CSVRecord record) {
        return getCsvColumnAsString(record, HEADER_NAME).orElseThrow(() -> new IllegalStateException(SURVEY_NAME_EMPTY));
    }

    @Named("toExpectedCompletes")
    default int toExpectedCompletes(CSVRecord record) {
        return getCsvColumnAsInteger(record, HEADER_EXPECTED_COMPLETES).orElseThrow(() -> new IllegalStateException(EXPECTED_COMPLETES_EMPTY));
    }

    @Named("toCompletionPoints")
    default int toCompletionPoints(CSVRecord record) {
        return getCsvColumnAsInteger(record, HEADER_COMPLETION_POINTS).orElseThrow(() -> new IllegalStateException(COMPLETION_POINTS_EMPTY));
    }

    @Named("toFilteredPoints")
    default int toFilteredPoints(CSVRecord record) {
        return getCsvColumnAsInteger(record, HEADER_FILTERED_POINTS).orElseThrow(() -> new IllegalStateException(FILTERED_POINTS_EMPTY));
    }
}
