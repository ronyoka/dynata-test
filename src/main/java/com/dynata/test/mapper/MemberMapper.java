package com.dynata.test.mapper;

import static com.dynata.test.mapper.CsvColumnConstants.ACTIVE_VALUE;
import static com.dynata.test.mapper.CsvColumnConstants.EMAIL_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.FULL_NAME_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_EMAIL_ADDRESS;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_FULL_NAME;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_IS_ACTIVE;
import static com.dynata.test.mapper.CsvColumnConstants.HEADER_MEMBER_ID;
import static com.dynata.test.mapper.CsvColumnConstants.IS_ACTIVE_EMPTY;
import static com.dynata.test.mapper.CsvColumnConstants.MEMBER_ID_EMPTY;

import com.dynata.test.model.Member;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting CSV records to Member objects.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends BaseCsvRecordMapper {

    /**
     * Maps a CSV record to a Member object.
     *
     * @param record the CSV record
     * @return the Member object
     */
    @Mapping(source = "record", target = "id", qualifiedByName = "toMemberId")
    @Mapping(source = "record", target = "fullname", qualifiedByName = "toFullName")
    @Mapping(source = "record", target = "email", qualifiedByName = "toEmail")
    @Mapping(source = "record", target = "active", qualifiedByName = "toActive")
    Member toMember(CSVRecord record);

    @Named("toMemberId")
    default int toMemberId(CSVRecord record) {
        return getCsvColumnAsInteger(record, HEADER_MEMBER_ID).orElseThrow(() -> new IllegalStateException(MEMBER_ID_EMPTY));
    }

    @Named("toFullName")
    default String toFullName(CSVRecord record) {
        return getCsvColumnAsString(record, HEADER_FULL_NAME).map(String::trim).orElseThrow(() -> new IllegalStateException(FULL_NAME_EMPTY));
    }

    @Named("toEmail")
    default String toEmail(CSVRecord record) {
        return getCsvColumnAsString(record, HEADER_EMAIL_ADDRESS).orElseThrow(() -> new IllegalStateException(EMAIL_EMPTY));
    }

    @Named("toActive")
    default boolean toActive(CSVRecord record) {
        return getCsvColumnAsString(record, HEADER_IS_ACTIVE)
                .map(ACTIVE_VALUE::equals)
                .orElseThrow(() -> new IllegalStateException(IS_ACTIVE_EMPTY));
    }
}
