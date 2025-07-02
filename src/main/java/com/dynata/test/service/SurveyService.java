package com.dynata.test.service;

import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Service for survey-related operations.
 */
public interface SurveyService {

    /**
     * Fetches all the respondents who completed the questionnaire for the given survey id.
     *
     * @param surveyId the survey ID
     * @return a list of members who completed the survey
     */
    List<Member> getRespondentsWhoCompletedSurvey(int surveyId);

    /**
     * Fetches all the surveys that were completed by the given member id.
     *
     * @param memberId the member ID
     * @return a list of surveys completed by the member
     */
    List<Survey> getSurveysCompletedByMember(int memberId);

    /**
     * Fetches the list of points (with the related survey id) that the member collected so far.
     *
     * @param memberId the member ID
     * @return a map of survey IDs to points collected
     */
    Map<Integer, Integer> getPointsCollectedByMember(int memberId);

    /**
     * Fetches the list of members who can be invited for the given survey
     * (not participated in that survey yet and active).
     *
     * @param surveyId the survey ID
     * @return a list of members who can be invited
     */
    List<Member> getMembersWhoCanBeInvitedForSurvey(int surveyId);

    /**
     * Fetches the list of surveys with statistics.
     *
     * @return a list of survey statistics
     */
    List<SurveyStatistics> getSurveyStatistics();

    /**
     * Inner class representing survey statistics.
     */
    @Getter
    @AllArgsConstructor
    @Builder
    class SurveyStatistics {
        private int surveyId;
        private String surveyName;
        private int numberOfCompletes;
        private int numberOfFilteredParticipants;
        private int numberOfRejectedParticipants;
        private double averageLengthOfTime;
    }
}