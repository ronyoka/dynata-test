package com.dynata.test.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dynata.test.model.Member;
import com.dynata.test.model.Participation;
import com.dynata.test.model.Status;
import com.dynata.test.model.Survey;
import com.dynata.test.service.DataService;
import com.dynata.test.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the SurveyService interface.
 */
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final DataService dataService;

    @Override
    public List<Member> getRespondentsWhoCompletedSurvey(int surveyId) {
        return dataService.getAllParticipations()
                          .stream()
                          .filter(p -> p.getSurveyId() == surveyId)
                          .filter(p -> p.getStatus().equals(Status.COMPLETED))
                          .map(p -> dataService.getMemberById(p.getMemberId()))
                          .toList();
    }

    @Override
    public List<Survey> getSurveysCompletedByMember(int memberId) {
        return dataService.getAllParticipations()
                          .stream()
                          .filter(p -> p.getMemberId() == memberId)
                          .filter(p -> p.getStatus().equals(Status.COMPLETED))
                          .map(p -> dataService.getSurveyById(p.getSurveyId()))
                          .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, Integer> getPointsCollectedByMember(int memberId) {
        Map<Integer, Integer> pointsMap = new HashMap<>();

        dataService.getAllParticipations()
                   .stream()
                   .filter(p -> p.getMemberId() == memberId)
                   .filter(Participation::isEligibleForPoints)
                   .forEach(p -> {
                       Survey survey = dataService.getSurveyById(p.getSurveyId());
                       int points = p.getStatus().equals(Status.COMPLETED)
                               ? survey.getCompletionPoints()
                               : survey.getFilteredPoints();
                       pointsMap.put(survey.getId(), points);
                   });

        return pointsMap;
    }

    @Override
    public List<Member> getMembersWhoCanBeInvitedForSurvey(int surveyId) {
        List<Integer> participatedMemberIds = dataService.getAllParticipations().stream()
                                                         .filter(p -> p.getSurveyId() == surveyId)
                                                         .map(Participation::getMemberId)
                                                         .toList();

        return dataService.getAllMembers()
                          .stream()
                          .filter(Member::isActive)
                          .filter(m -> !participatedMemberIds.contains(m.getId()))
                          .collect(Collectors.toList());
    }

    @Override
    public List<SurveyStatistics> getSurveyStatistics() {
        return dataService.getAllSurveys().stream()
                          .map(this::createSurveyStatistics)
                          .collect(Collectors.toList());
    }

    private SurveyStatistics createSurveyStatistics(Survey survey) {
        List<Participation> surveyParticipation = dataService.getAllParticipations().stream()
                                                             .filter(p -> p.getSurveyId() == survey.getId())
                                                             .toList();

        int numberOfCompletes = (int) surveyParticipation.stream()
                                                         .filter(p -> p.getStatus().equals(Status.COMPLETED))
                                                         .count();

        int numberOfFilteredParticipants = (int) surveyParticipation.stream()
                                                                    .filter(p -> p.getStatus().equals(Status.FILTERED))
                                                                    .count();

        int numberOfRejectedParticipants = (int) surveyParticipation.stream()
                                                                    .filter(p -> p.getStatus().equals(Status.REJECTED))
                                                                    .count();

        double averageLengthOfTime = surveyParticipation.stream()
                                                        .filter(p -> p.getStatus().equals(Status.COMPLETED))
                                                        .mapToInt(Participation::getLength)
                                                        .average()
                                                        .orElse(0);

        return SurveyStatistics.builder()
                               .surveyId(survey.getId())
                               .surveyName(survey.getName())
                               .numberOfCompletes(numberOfCompletes)
                               .numberOfFilteredParticipants(numberOfFilteredParticipants)
                               .numberOfRejectedParticipants(numberOfRejectedParticipants)
                               .averageLengthOfTime(averageLengthOfTime)
                               .build();
    }
}
