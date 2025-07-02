package com.dynata.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.impl.MemberServiceImpl;
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
@DisplayName("Member Service Tests")
class MemberServiceTest {

    @Mock
    private DataService dataService;

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private MemberServiceImpl memberService;

    private Member member1;
    private Member member2;
    private Member member3;
    private Survey survey1;

    @BeforeEach
    void setUp() {
        member1 = Member.builder().id(1).fullname("John Doe").email("john@example.com").active(true).build();
        member2 = Member.builder().id(2).fullname("Jane Smith").email("jane@example.com").active(true).build();
        member3 = Member.builder().id(3).fullname("Bob Johnson").email("bob@example.com").active(false).build();

        survey1 = Survey.builder().id(1).name("Survey 1").expectedCompletes(100).completionPoints(10).filteredPoints(5).build();

        given(dataService.getAllMembers()).willReturn(List.of(member1, member2, member3));
        given(dataService.getMemberById(1)).willReturn(member1);
        given(dataService.getMemberById(2)).willReturn(member2);
        given(dataService.getMemberById(3)).willReturn(member3);
        given(dataService.getMemberById(4)).willReturn(null);
    }

    @Test
    @DisplayName("Should return all members")
    void getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        assertEquals(3, members.size());
        assertEquals(member1, members.get(0));
        assertEquals(member2, members.get(1));
        assertEquals(member3, members.get(2));
    }

    @Test
    @DisplayName("Should return member by ID when found or null when not found")
    void getMemberById() {
        Member member = memberService.getMemberById(1);
        assertEquals(member1, member);

        member = memberService.getMemberById(4);
        assertNull(member);
    }

    @Test
    @DisplayName("Should return active members")
    void getActiveMembers() {
        List<Member> members = memberService.getActiveMembers();
        assertEquals(2, members.size());
        assertEquals(member1, members.get(0));
        assertEquals(member2, members.get(1));
    }

    @Test
    @DisplayName("Should return surveys completed by member")
    void getSurveysCompletedByMember() {
        given(surveyService.getSurveysCompletedByMember(1)).willReturn(List.of(survey1));

        List<Survey> surveys = memberService.getSurveysCompletedByMember(1);
        assertEquals(1, surveys.size());
        assertEquals(survey1, surveys.getFirst());
    }

    @Test
    @DisplayName("Should return points collected by member")
    void getPointsCollectedByMember() {
        Map<Integer, Integer> pointsMap = new HashMap<>();
        pointsMap.put(1, 10);
        pointsMap.put(2, 5);
        given(surveyService.getPointsCollectedByMember(1)).willReturn(pointsMap);

        Map<Integer, Integer> points = memberService.getPointsCollectedByMember(1);
        assertEquals(2, points.size());
        assertEquals(10, points.get(1));
        assertEquals(5, points.get(2));
    }
}
