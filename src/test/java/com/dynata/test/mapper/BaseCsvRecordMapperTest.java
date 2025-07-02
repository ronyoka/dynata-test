package com.dynata.test.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Base CSV Record Mapper Tests")
class BaseCsvRecordMapperTest extends BaseMapperTest {

    private BaseCsvRecordMapper mapper;
    private CSVRecord record;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new BaseCsvRecordMapper() {
        };
        record = parseFirstCsvRecord("id,name,value\n1,test,123");
    }

    @Test
    @DisplayName("Should return string value when column exists")
    void getCsvColumnAsStringShouldReturnValueWhenColumnExists() {
        var result = mapper.getCsvColumnAsString(record, "name");

        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }

    @Test
    @DisplayName("Should throw exception when column does not exist")
    void getCsvColumnAsStringShouldThrowExceptionWhenColumnDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> mapper.getCsvColumnAsString(record, "nonexistent"));
    }

    @Test
    @DisplayName("Should return empty when column value is blank")
    void getCsvColumnAsStringShouldReturnEmptyWhenColumnValueIsBlank() throws IOException {
        var blankRecord = parseFirstCsvRecord("id,name,value\n1,,123");

        var result = mapper.getCsvColumnAsString(blankRecord, "name");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Should return integer value when column exists and is integer")
    void getCsvColumnAsIntegerShouldReturnValueWhenColumnExistsAndIsInteger() {
        var result = mapper.getCsvColumnAsInteger(record, "value");

        assertTrue(result.isPresent());
        assertEquals(123, result.get());
    }

    @Test
    @DisplayName("Should throw exception when column does not exist for integer")
    void getCsvColumnAsIntegerShouldThrowExceptionWhenColumnDoesNotExist() {
        assertThrows(IllegalArgumentException.class, () -> mapper.getCsvColumnAsInteger(record, "nonexistent"));
    }

    @Test
    @DisplayName("Should throw exception when column value is not an integer")
    void getCsvColumnAsIntegerShouldThrowExceptionWhenColumnValueIsNotInteger() throws IOException {
        var nonIntegerRecord = parseFirstCsvRecord("id,name,value\n1,test,not-an-integer");

        assertThrows(NumberFormatException.class, () -> mapper.getCsvColumnAsInteger(nonIntegerRecord, "value"));
    }
}
