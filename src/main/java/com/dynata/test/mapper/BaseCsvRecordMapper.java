package com.dynata.test.mapper;

import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * Base interface for mapping CSV records to specific object fields.
 * Provides utility methods for retrieving and parsing data from CSV columns.
 * This interface is designed to be extended by specific mapper implementations
 * for mapping CSV records to domain objects.
 */
public interface BaseCsvRecordMapper {

    default Optional<Integer> getCsvColumnAsInteger(CSVRecord record, String columnName) {
        return getCsvColumnAsString(record, columnName).map(Integer::parseInt);

    }

    default Optional<String> getCsvColumnAsString(CSVRecord record, String columnName) {
        return Optional.ofNullable(record.get(columnName))
                       .filter(StringUtils::isNotBlank)
                       .map(String::trim);
    }
}
