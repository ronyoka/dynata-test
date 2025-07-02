package com.dynata.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.MemberService;
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
@DisplayName("Member Controller Tests")
class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    private Member member1;
    private Member member2;
    private Member member3;
    private Survey survey1;
    private Survey survey2;

    @BeforeEach
    void setUp() {
        // Create test data
        member1 = Member.builder().id(1).fullname("John Doe").email("john@example.com").active(true).build();
        member2 = Member.builder().id(2).fullname("Jane Smith").email("jane@example.com").active(true).build();
        member3 = Member.builder().id(3).fullname("Bob Johnson").email("bob@example.com").active(false).build();

        survey1 = Survey.builder().id(1).name("Survey 1").expectedCompletes(100).completionPoints(10).filteredPoints(5).build();
        survey2 = Survey.builder().id(2).name("Survey 2").expectedCompletes(200).completionPoints(20).filteredPoints(10).build();
    }

    @Test
    @DisplayName("Should return all members")
    void getAllMembers() {
        given(memberService.getAllMembers()).willReturn(List.of(member1, member2, member3));

        ResponseEntity<List<Member>> response = memberController.getAllMembers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals(member1, response.getBody().get(0));
        assertEquals(member2, response.getBody().get(1));
        assertEquals(member3, response.getBody().get(2));
    }

    @Test
    @DisplayName("Should return member when found by ID")
    void getMemberById_Found() {
        given(memberService.getMemberById(1)).willReturn(member1);

        ResponseEntity<Member> response = memberController.getMemberById(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(member1, response.getBody());
    }

    @Test
    @DisplayName("Should return 404 when member not found by ID")
    void getMemberById_NotFound() {
        given(memberService.getMemberById(4)).willReturn(null);

        ResponseEntity<Member> response = memberController.getMemberById(4);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Should return active members")
    void getActiveMembers() {
        given(memberService.getActiveMembers()).willReturn(List.of(member1, member2));

        ResponseEntity<List<Member>> response = memberController.getActiveMembers();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(member1, response.getBody().get(0));
        assertEquals(member2, response.getBody().get(1));
    }

    @Test
    @DisplayName("Should return surveys completed by member")
    void getSurveysCompletedByMember() {
        given(memberService.getSurveysCompletedByMember(1)).willReturn(List.of(survey1, survey2));

        ResponseEntity<List<Survey>> response = memberController.getSurveysCompletedByMember(1);

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
        given(memberService.getPointsCollectedByMember(1)).willReturn(pointsMap);

        ResponseEntity<Map<Integer, Integer>> response = memberController.getPointsCollectedByMember(1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(10, response.getBody().get(1));
        assertEquals(5, response.getBody().get(2));
    }
}
