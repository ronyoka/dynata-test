package com.dynata.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("Survey Controller Tests")
class SurveyControllerTest {

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private SurveyController surveyController;

    private Member member1;
    private Member member2;
    private Survey survey1;
    private Survey survey2;
    private SurveyService.SurveyStatistics stats1;
    private SurveyService.SurveyStatistics stats2;

    @BeforeEach
    void setUp() {
        // Create test data
        member1 = Member.builder().id(1).fullname("John Doe").email("john@example.com").active(true).build();
        member2 = Member.builder().id(2).fullname("Jane Smith").email("jane@example.com").active(true).build();

        survey1 = Survey.builder().id(1).name("Survey 1").expectedCompletes(100).completionPoints(10).filteredPoints(5).build();
        survey2 = Survey.builder().id(2).name("Survey 2").expectedCompletes(200).completionPoints(20).filteredPoints(10).build();

        stats1 = new SurveyService.SurveyStatistics(1, "Survey 1", 10, 5, 2, 15.0);
        stats2 = new SurveyService.SurveyStatistics(2, "Survey 2", 20, 10, 5, 20.0);
    }

    @Test
    @DisplayName("Should return respondents who completed the survey")
    void getRespondentsWhoCompletedSurvey() {
        given(surveyService.getRespondentsWhoCompletedSurvey(1)).willReturn(List.of(member1, member2));

        ResponseEntity<List<Member>> response = surveyController.getRespondentsWhoCompletedSurvey(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(member1, response.getBody().get(0));
        assertEquals(member2, response.getBody().get(1));
    }

    @Test
    @DisplayName("Should return members who can be invited for the survey")
    void getMembersWhoCanBeInvitedForSurvey() {
        given(surveyService.getMembersWhoCanBeInvitedForSurvey(1)).willReturn(List.of(member2));

        ResponseEntity<List<Member>> response = surveyController.getMembersWhoCanBeInvitedForSurvey(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(member2, response.getBody().getFirst());
    }

    @Test
    @DisplayName("Should return survey statistics")
    void getSurveyStatistics() {
        given(surveyService.getSurveyStatistics()).willReturn(List.of(stats1, stats2));

        ResponseEntity<List<SurveyService.SurveyStatistics>> response = surveyController.getSurveyStatistics();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(stats1, response.getBody().get(0));
        assertEquals(stats2, response.getBody().get(1));
    }

    @Test
    @DisplayName("Should return surveys completed by member")
    void getSurveysCompletedByMember() {
        given(surveyService.getSurveysCompletedByMember(1)).willReturn(List.of(survey1, survey2));

        ResponseEntity<List<Survey>> response = surveyController.getSurveysCompletedByMember(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(survey1, response.getBody().get(0));
        assertEquals(survey2, response.getBody().get(1));
    }

    @Test
    @DisplayName("Should return points collected by member")
    void getPointsCollectedByMember() {
        Map<Integer, Integer> pointsMap = new HashMap<>();
        pointsMap.put(1, 10);
        pointsMap.put(2, 5);
        given(surveyService.getPointsCollectedByMember(1)).willReturn(pointsMap);

        ResponseEntity<Map<Integer, Integer>> response = surveyController.getPointsCollectedByMember(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(10, response.getBody().get(1));
        assertEquals(5, response.getBody().get(2));
    }
}
