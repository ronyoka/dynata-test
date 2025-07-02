package com.dynata.test.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;
import com.dynata.test.service.DataService;
import com.dynata.test.service.MemberService;
import com.dynata.test.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the MemberService interface.
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final DataService dataService;
    private final SurveyService surveyService;

    @Override
    public List<Member> getAllMembers() {
        return dataService.getAllMembers();
    }

    @Override
    public Member getMemberById(int id) {
        return dataService.getMemberById(id);
    }

    @Override
    public List<Member> getActiveMembers() {
        return dataService.getAllMembers().stream()
                          .filter(Member::isActive)
                          .collect(Collectors.toList());
    }

    @Override
    public List<Survey> getSurveysCompletedByMember(int memberId) {
        return surveyService.getSurveysCompletedByMember(memberId);
    }

    @Override
    public Map<Integer, Integer> getPointsCollectedByMember(int memberId) {
        return surveyService.getPointsCollectedByMember(memberId);
    }
}