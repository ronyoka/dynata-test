package com.dynata.test.service;

import com.dynata.test.model.Member;
import com.dynata.test.model.Survey;

import java.util.List;
import java.util.Map;

/**
 * Service for member-related operations.
 */
public interface MemberService {

    /**
     * Gets all members.
     *
     * @return a list of all members
     */
    List<Member> getAllMembers();

    /**
     * Gets a member by ID.
     *
     * @param id the member ID
     * @return the member with the given ID, or null if not found
     */
    Member getMemberById(int id);

    /**
     * Gets all active members.
     *
     * @return a list of all active members
     */
    List<Member> getActiveMembers();

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
}