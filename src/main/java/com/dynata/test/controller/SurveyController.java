package com.dynata.test.controller;

import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for survey-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * Gets all respondents who completed the questionnaire for the given survey id.
     *
     * @param surveyId the survey ID
     * @return a list of members who completed the survey
     */
    @GetMapping("/{surveyId}/respondents")
    public ResponseEntity<List<Member>> getRespondentsWhoCompletedSurvey(@PathVariable int surveyId) {
        return ResponseEntity.ok(surveyService.getRespondentsWhoCompletedSurvey(surveyId));
    }

    /**
     * Gets all members who can be invited for the given survey.
     *
     * @param surveyId the survey ID
     * @return a list of members who can be invited
     */
    @GetMapping("/{surveyId}/invitable-members")
    public ResponseEntity<List<Member>> getMembersWhoCanBeInvitedForSurvey(@PathVariable int surveyId) {
        return ResponseEntity.ok(surveyService.getMembersWhoCanBeInvitedForSurvey(surveyId));
    }

    /**
     * Gets all surveys with statistics.
     *
     * @return a list of survey statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<SurveyService.SurveyStatistics>> getSurveyStatistics() {
        return ResponseEntity.ok(surveyService.getSurveyStatistics());
    }

    /**
     * Gets all surveys completed by the given member id.
     *
     * @param memberId the member ID
     * @return a list of surveys completed by the member
     */
    @GetMapping("/completed-by/{memberId}")
    public ResponseEntity<List<Survey>> getSurveysCompletedByMember(@PathVariable int memberId) {
        return ResponseEntity.ok(surveyService.getSurveysCompletedByMember(memberId));
    }

    /**
     * Gets the points collected by the given member id.
     *
     * @param memberId the member ID
     * @return a map of survey IDs to points collected
     */
    @GetMapping("/points-collected-by/{memberId}")
    public ResponseEntity<Map<Integer, Integer>> getPointsCollectedByMember(@PathVariable int memberId) {
        return ResponseEntity.ok(surveyService.getPointsCollectedByMember(memberId));
    }
}