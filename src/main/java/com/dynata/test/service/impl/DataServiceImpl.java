package com.dynata.test.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.dynata.test.mapper.MemberMapper;
import com.dynata.test.mapper.ParticipationMapper;
import com.dynata.test.mapper.StatusMapper;
import com.dynata.test.mapper.SurveyMapper;
import com.dynata.test.model.Member;
import com.dynata.test.model.Participation;
import com.dynata.test.model.Status;
import com.dynata.test.model.Survey;
import com.dynata.test.service.DataService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Implementation of the DataService interface.
 * Loads data from CSV files and provides access to it.
 */
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private static final String MEMBERS_CSV = "members.csv";
    private static final String SURVEYS_CSV = "surveys.csv";
    private static final String STATUSES_CSV = "statuses.csv";
    private static final String PARTICIPATION_CSV = "participation.csv";
    private final MemberMapper memberMapper;
    private final SurveyMapper surveyMapper;
    private final StatusMapper statusMapper;
    private final ParticipationMapper participationMapper;

    private CopyOnWriteArrayList<Member> members;
    private CopyOnWriteArrayList<Survey> surveys;
    private CopyOnWriteArrayList<Status> statuses;
    private CopyOnWriteArrayList<Participation> participation;

    private ConcurrentHashMap<Integer, Member> membersMap;
    private ConcurrentHashMap<Integer, Survey> surveysMap;
    private ConcurrentHashMap<Integer, Status> statusesMap;

    /**
     * Loads all required data from corresponding CSV files into memory.
     * The method initializes and populates the internal collections and maps
     * for members, surveys, statuses, and participation records, ensuring
     * that the data is available for later access.
     * <p>
     * This method is executed after the bean is constructed due to the
     * {@code @PostConstruct} annotation. It invokes specific helper methods
     * to handle the loading of each type of data.
     * <p>
     * If an I/O error occurs during the loading process, an unchecked
     * {@link RuntimeException} is thrown with a descriptive error message.
     *
     * @throws RuntimeException if any IOException occurs while reading the CSV files.
     */
    @PostConstruct
    @Override
    public void loadData() {
        try {
            loadMembers();
            loadSurveys();
            loadStatuses();
            loadParticipation();
        } catch (IOException e) {
            throw new RuntimeException("Error loading data from CSV files", e);
        }
    }

    private void loadMembers() throws IOException {
        members = new CopyOnWriteArrayList<>();
        membersMap = new ConcurrentHashMap<>();

        try (Reader reader = new InputStreamReader(new ClassPathResource(MEMBERS_CSV).getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = createCSVParser(reader)) {

            for (CSVRecord record : csvParser) {
                Member member = memberMapper.toMember(record);
                members.add(member);
                membersMap.put(member.getId(), member);
            }
        }
    }

    private void loadSurveys() throws IOException {
        surveys = new CopyOnWriteArrayList<>();
        surveysMap = new ConcurrentHashMap<>();

        try (Reader reader = new InputStreamReader(new ClassPathResource(SURVEYS_CSV).getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = createCSVParser(reader)) {

            for (CSVRecord record : csvParser) {
                Survey survey = surveyMapper.toSurvey(record);
                surveys.add(survey);
                surveysMap.put(survey.getId(), survey);
            }
        }
    }

    private void loadStatuses() throws IOException {
        statuses = new CopyOnWriteArrayList<>();
        statusesMap = new ConcurrentHashMap<>();

        try (Reader reader = new InputStreamReader(new ClassPathResource(STATUSES_CSV).getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = createCSVParser(reader)) {

            for (CSVRecord record : csvParser) {
                Status status = statusMapper.toStatus(record);
                statuses.add(status);
                statusesMap.put(status.getId(), status);
            }
        }
    }

    private void loadParticipation() throws IOException {
        participation = new CopyOnWriteArrayList<>();

        try (Reader reader = new InputStreamReader(new ClassPathResource(PARTICIPATION_CSV).getInputStream(), StandardCharsets.UTF_8);
             CSVParser csvParser = createCSVParser(reader)) {

            for (CSVRecord record : csvParser) {
                Participation participation = participationMapper.toParticipation(record, statusesMap);
                this.participation.add(participation);
            }
        }
    }

    @Override
    public List<Member> getAllMembers() {
        return members;
    }

    @Override
    public List<Survey> getAllSurveys() {
        return surveys;
    }

    @Override
    public List<Status> getAllStatuses() {
        return statuses;
    }

    @Override
    public List<Participation> getAllParticipations() {
        return participation;
    }

    @Override
    public Member getMemberById(int id) {
        return membersMap.get(id);
    }

    @Override
    public Survey getSurveyById(int id) {
        return surveysMap.get(id);
    }

    @Override
    public Status getStatusById(int id) {
        return statusesMap.get(id);
    }

    @Override
    public Map<Integer, Member> getMembersMap() {
        return membersMap;
    }

    @Override
    public Map<Integer, Survey> getSurveysMap() {
        return surveysMap;
    }

    @Override
    public Map<Integer, Status> getStatusesMap() {
        return statusesMap;
    }
}
