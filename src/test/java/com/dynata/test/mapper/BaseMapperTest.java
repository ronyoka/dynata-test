package com.dynata.test.mapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * BaseMapperTest is an abstract class that provides utility methods for parsing and handling
 * CSV data. It is designed to assist in testing mappers that transform CSV data into domain
 * objects or entities. The methods in this class facilitate the creation of a CSV format,
 * parsing CSV data into records, and retrieving the first record from the parsed data.
 */
public abstract class BaseMapperTest {
    /**
     * Creates and returns a configured CSVFormat object for parsing CSV data.
     * The format is set to use the default settings, includes headers, and skips the header record during processing.
     *
     * @return a CSVFormat object configured with default settings, header inclusion, and skipping the header record.
     */
    public CSVFormat createCsvFormat() {
        return CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
    }

    /**
     * Parses the provided CSV data string and returns a list of CSV records.
     * The method utilizes a predefined CSV format to parse the data, including
     * configurations for handling headers.
     *
     * @param csvData the CSV data as a string to be parsed
     * @return a list of CSV records parsed from the input string
     * @throws IOException if an I/O error occurs during parsing
     */
    public List<CSVRecord> parseCsvData(String csvData) throws IOException {
        return createCsvFormat().parse(new StringReader(csvData)).getRecords();
    }

    /**
     * Parses the provided CSV data string and retrieves the first record.
     * This method utilizes a predefined CSV format for parsing the data and
     * assumes the data includes a header row that is skipped during parsing.
     *
     * @param csvData the CSV data as a string to be parsed
     * @return the first CSVRecord parsed from the input data
     * @throws IOException if an I/O error occurs during parsing
     */
    public CSVRecord parseFirstCsvRecord(String csvData) throws IOException {
        return parseCsvData(csvData).getFirst();
    }
}
