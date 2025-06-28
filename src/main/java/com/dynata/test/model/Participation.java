package com.dynata.test.model;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a member's participation in a survey.
 */
@Getter
@AllArgsConstructor
@ToString
public class Participation {
    private int id;
    private int memberId;
    private int surveyId;
    private Status status;
    private int length;

    /**
     * Determines if this participation is eligible for points.
     * According to requirements, points are awarded for FILTERED and COMPLETED statuses.
     *
     * @return true if eligible for points, false otherwise
     */
    public boolean isEligibleForPoints() {
        return Optional.ofNullable(status)
                       .map(Participation::isStatusFilteredOrCompleted)
                       .orElse(false);
    }

    private static boolean isStatusFilteredOrCompleted(Status s) {
        return s.equals(Status.FILTERED) || s.equals(Status.COMPLETED);
    }
}
