package com.dynata.test.mapper;

import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

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
