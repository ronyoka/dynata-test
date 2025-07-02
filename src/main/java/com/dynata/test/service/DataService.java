package com.dynata.test.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Participation;
import com.dynata.test.model.Status;
import com.dynata.test.model.Survey;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

/**
 * Service for loading and accessing data from CSV files.
 */
public interface DataService {

    /**
     * Loads all data from CSV files into memory.
     */
    void loadData();

    /**
     * Gets all members.
     *
     * @return a list of all members
     */
    List<Member> getAllMembers();

    /**
     * Gets all surveys.
     *
     * @return a list of all surveys
     */
    List<Survey> getAllSurveys();

    /**
     * Gets all statuses.
     *
     * @return a list of all statuses
     */
    List<Status> getAllStatuses();

    /**
     * Gets all participations.
     *
     * @return a list of all participations
     */
    List<Participation> getAllParticipations();

    /**
     * Gets a member by ID.
     *
     * @param id the member ID
     * @return the member with the given ID, or null if not found
     */
    Member getMemberById(int id);

    /**
     * Gets a survey by ID.
     *
     * @param id the survey ID
     * @return the survey with the given ID, or null if not found
     */
    Survey getSurveyById(int id);

    /**
     * Gets a status by ID.
     *
     * @param id the status ID
     * @return the status with the given ID, or null if not found
     */
    Status getStatusById(int id);

    /**
     * Gets a map of all members by ID.
     *
     * @return a map of member IDs to members
     */
    Map<Integer, Member> getMembersMap();

    /**
     * Gets a map of all surveys by ID.
     *
     * @return a map of survey IDs to surveys
     */
    Map<Integer, Survey> getSurveysMap();

    /**
     * Gets a map of all statuses by ID.
     *
     * @return a map of status IDs to statuses
     */
    Map<Integer, Status> getStatusesMap();


    /**
     * Creates and configures a CSVParser for reading CSV data from the provided Reader.
     * The parser is set up to use the default CSV format, automatically recognizes headers,
     * and skips the header record when parsing.
     *
     * @param reader the Reader from which CSV data will be read
     * @return a CSVParser instance for parsing the provided CSV data
     * @throws IOException if there is an error reading from the provided Reader
     */
    default CSVParser createCSVParser(Reader reader) throws IOException {
        return CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build().parse(reader);
    }
}