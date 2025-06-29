package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.NAME;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS_ID;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS_ID_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.STATUS_NAME_EMPTY;

import java.util.Optional;

import com.dynata.test.model.Status;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting CSV records to Status objects.
 */
@Mapper(componentModel = "spring")
public interface StatusMapper extends BaseCsvRecordMapper {

    /**
     * Maps a CSV record to a Status object.
     *
     * @param record the CSV record
     * @return the Status object
     */
    @Mapping(source = "record", target = "id", qualifiedByName = "toStatusId")
    @Mapping(source = "record", target = "name", qualifiedByName = "toName")
    Status toStatus(CSVRecord record);

    @Named("toStatusId")
    default int toStatusId(CSVRecord record) {
        return Optional.ofNullable(record.get(STATUS_ID))
                       .filter(StringUtils::isNotBlank)
                       .map(String::trim)
                       .map(Integer::parseInt)
                       .orElseThrow(() -> new IllegalStateException(STATUS_ID_EMPTY));
    }

    @Named("toName")
    default String toName(CSVRecord record) {
        return Optional.ofNullable(record.get(NAME))
                       .filter(StringUtils::isNotBlank)
                       .map(String::trim)
                       .orElseThrow(() -> new IllegalStateException(STATUS_NAME_EMPTY));
    }
}
