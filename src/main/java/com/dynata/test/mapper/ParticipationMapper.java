package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.LENGTH;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS_NOT_FOUND;

import java.util.Map;
import java.util.Optional;

import com.dynata.test.model.Participation;
import com.dynata.test.model.Status;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting CSV records to Participation objects.
 */
@Component
public class ParticipationMapper implements BaseCsvRecordMapper {

    private final MemberMapper memberMapper;
    private final SurveyMapper surveyMapper;

    @Autowired
    public ParticipationMapper(MemberMapper memberMapper, SurveyMapper surveyMapper) {
        this.memberMapper = memberMapper;
        this.surveyMapper = surveyMapper;
    }

    /**
     * Maps a CSV record to a Participation object.
     *
     * @param record    the CSV record
     * @param statusMap a map of status IDs to Status objects
     * @return the Participation object
     */
    public Participation toParticipation(CSVRecord record, Map<Integer, Status> statusMap) {
        return Participation.builder()
                            .memberId(memberMapper.toMemberId(record))
                            .surveyId(surveyMapper.toSurveyId(record))
                            .status(toStatus(record, statusMap))
                            .length(toLength(record))
                            .build();
    }

    private Status toStatus(CSVRecord record, Map<Integer, Status> statusMap) {
        return Optional.ofNullable(statusMap.get(getStatusId(record)))
                       .orElseThrow(() -> new IllegalStateException(String.format(STATUS_NOT_FOUND, getStatusId(record))));
    }

    private Integer getStatusId(CSVRecord record) {
        return getCsvColumnAsInteger(record, STATUS).orElseThrow(() -> new IllegalStateException(STATUS_EMPTY));
    }

    private int toLength(CSVRecord record) {
        return getCsvColumnAsInteger(record, LENGTH).orElse(0);
    }
}
