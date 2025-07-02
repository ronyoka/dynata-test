package com.dynata.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Participation;
import com.dynata.test.model.Status;
import com.dynata.test.model.Survey;
import com.dynata.test.service.impl.SurveyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Survey Service Tests")
class SurveyServiceTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    private Member member1;
    private Member member2;
    private Survey survey1;

    @BeforeEach
    void setUp() {
        member1 = Member.builder().id(1).fullname("John Doe").email("john@example.com").active(true).build();
        member2 = Member.builder().id(2).fullname("Jane Smith").email("jane@example.com").active(true).build();
        Member member3 = Member.builder().id(3).fullname("Bob Johnson").email("bob@example.com").active(false).build();

        survey1 = Survey.builder().id(1).name("Survey 1").expectedCompletes(100).completionPoints(10).filteredPoints(5).build();
        Survey survey2 = Survey.builder().id(2).name("Survey 2").expectedCompletes(200).completionPoints(20).filteredPoints(10).build();

        Participation participation1 = Participation.builder().memberId(1).surveyId(1).status(Status.COMPLETED).length(15).build();
        Participation participation2 = Participation.builder().memberId(1).surveyId(2).status(Status.FILTERED).length(0).build();
        Participation participation3 = Participation.builder().memberId(2).surveyId(1).status(Status.REJECTED).length(0).build();
        Participation participation4 = Participation.builder().memberId(3).surveyId(2).status(Status.COMPLETED).length(20).build();

        given(dataService.getAllMembers()).willReturn(List.of(member1, member2, member3));
        given(dataService.getAllSurveys()).willReturn(List.of(survey1, survey2));
        given(dataService.getAllParticipations()).willReturn(List.of(participation1, participation2, participation3, participation4));

        Map<Integer, Member> membersMap = new HashMap<>();
        membersMap.put(1, member1);
        membersMap.put(2, member2);
        membersMap.put(3, member3);
        given(dataService.getMembersMap()).willReturn(membersMap);
        given(dataService.getMemberById(1)).willReturn(member1);
        given(dataService.getMemberById(2)).willReturn(member2);
        given(dataService.getMemberById(3)).willReturn(member3);

        Map<Integer, Survey> surveysMap = new HashMap<>();
        surveysMap.put(1, survey1);
        surveysMap.put(2, survey2);
        given(dataService.getSurveysMap()).willReturn(surveysMap);
        given(dataService.getSurveyById(1)).willReturn(survey1);
        given(dataService.getSurveyById(2)).willReturn(survey2);
    }

    @Test
    @DisplayName("Should return respondents who completed the survey")
    void getRespondentsWhoCompletedSurvey() {
        List<Member> respondents = surveyService.getRespondentsWhoCompletedSurvey(1);
        assertEquals(1, respondents.size());
        assertEquals(member1, respondents.getFirst());
    }

    @Test
    @DisplayName("Should return surveys completed by member")
    void getSurveysCompletedByMember() {
        List<Survey> surveys = surveyService.getSurveysCompletedByMember(1);
        assertEquals(1, surveys.size());
        assertEquals(survey1, surveys.getFirst());
    }

    @Test
    @DisplayName("Should return points collected by member")
    void getPointsCollectedByMember() {
        Map<Integer, Integer> points = surveyService.getPointsCollectedByMember(1);
        assertEquals(2, points.size());
        assertEquals(10, points.get(1)); // Completion points for survey1
        assertEquals(10, points.get(2)); // Filtered points for survey2
    }

    @Test
    @DisplayName("Should return members who can be invited for the survey")
    void getMembersWhoCanBeInvitedForSurvey() {
        List<Member> members = surveyService.getMembersWhoCanBeInvitedForSurvey(2);
        assertEquals(1, members.size());
        assertEquals(member2, members.getFirst()); // Only member2 is active and has not participated in survey2
    }

    @Test
    @DisplayName("Should return survey statistics")
    void getSurveyStatistics() {
        List<SurveyService.SurveyStatistics> statistics = surveyService.getSurveyStatistics();
        assertEquals(2, statistics.size());

        SurveyService.SurveyStatistics stats1 = statistics.stream()
                                                          .filter(s -> s.getSurveyId() == 1)
                                                          .findFirst()
                                                          .orElse(null);
        Assertions.assertNotNull(stats1);
        assertEquals(1, stats1.getNumberOfCompletes());
        assertEquals(0, stats1.getNumberOfFilteredParticipants());
        assertEquals(1, stats1.getNumberOfRejectedParticipants());
        assertEquals(15.0, stats1.getAverageLengthOfTime());

        SurveyService.SurveyStatistics stats2 = statistics.stream()
                                                          .filter(s -> s.getSurveyId() == 2)
                                                          .findFirst()
                                                          .orElse(null);
        Assertions.assertNotNull(stats2);
        assertEquals(1, stats2.getNumberOfCompletes());
        assertEquals(1, stats2.getNumberOfFilteredParticipants());
        assertEquals(0, stats2.getNumberOfRejectedParticipants());
        assertEquals(20.0, stats2.getAverageLengthOfTime());
    }
}
