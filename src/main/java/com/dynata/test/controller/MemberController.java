package com.dynata.test.controller;

import java.util.List;
import java.util.Map;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for member-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * Gets all members.
     *
     * @return a list of all members
     */
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * Gets a member by ID.
     *
     * @param id the member ID
     * @return the member with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable int id) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(member);
    }

    /**
     * Gets all active members.
     *
     * @return a list of all active members
     */
    @GetMapping("/active")
    public ResponseEntity<List<Member>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }

    /**
     * Gets all surveys completed by the given member id.
     *
     * @param memberId the member ID
     * @return a list of surveys completed by the member
     */
    @GetMapping("/{memberId}/completed-surveys")
    public ResponseEntity<List<Survey>> getSurveysCompletedByMember(@PathVariable int memberId) {
        return ResponseEntity.ok(memberService.getSurveysCompletedByMember(memberId));
    }

    /**
     * Gets the points collected by the given member id.
     *
     * @param memberId the member ID
     * @return a map of survey IDs to points collected
     */
    @GetMapping("/{memberId}/points")
    public ResponseEntity<Map<Integer, Integer>> getPointsCollectedByMember(@PathVariable int memberId) {
        return ResponseEntity.ok(memberService.getPointsCollectedByMember(memberId));
    }
}