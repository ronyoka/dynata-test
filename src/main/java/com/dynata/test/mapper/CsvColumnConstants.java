package com.dynata.test.mapper;

/**
 * Constants for CSV column names and error messages used in mappers.
 */
public final class CsvColumnConstants {

    private CsvColumnConstants() {
    }

    public static final String MEMBER_ID = "Member Id";
    public static final String FULL_NAME = "Full name";
    public static final String EMAIL_ADDRESS = "E-mail address";
    public static final String IS_ACTIVE = "Is Active";
    public static final String STATUS = "Status";
    public static final String LENGTH = "Length";
    public static final String STATUS_ID = "Status Id";
    public static final String NAME = "Name";
    public static final String SURVEY_ID = "Survey Id";
    public static final String EXPECTED_COMPLETES = "Expected completes";
    public static final String COMPLETION_POINTS = "Completion points";
    public static final String FILTERED_POINTS = "Filtered points";

    public static final String MEMBER_ID_EMPTY = "Member Id not found in record";
    public static final String FULL_NAME_EMPTY = "Full name not found in record";
    public static final String EMAIL_EMPTY = "Email address not found in record";
    public static final String IS_ACTIVE_EMPTY = "Email address not found in record";
    public static final String STATUS_EMPTY = "Status cannot be empty";
    public static final String STATUS_NOT_FOUND = "Status with id %d not found";
    public static final String STATUS_ID_EMPTY = "Status Id cannot be empty";
    public static final String STATUS_NAME_EMPTY = "Status name cannot be empty";
    public static final String SURVEY_ID_EMPTY = "Survey Id cannot be empty";
    public static final String SURVEY_NAME_EMPTY = "Survey name cannot be empty";
    public static final String EXPECTED_COMPLETES_EMPTY = "Expected completes cannot be empty";
    public static final String COMPLETION_POINTS_EMPTY = "Completion points cannot be empty";
    public static final String FILTERED_POINTS_EMPTY = "Filtered points cannot be empty";

    public static final String ACTIVE_VALUE = "1";
}
