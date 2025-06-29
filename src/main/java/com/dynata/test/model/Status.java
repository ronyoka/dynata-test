package com.dynata.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents the participation status of a member in a survey.
 */
@Getter
@AllArgsConstructor
@Builder
public class Status {
    public static final Status NOT_ASKED = new Status(1, "Not asked");
    public static final Status REJECTED = new Status(2, "Rejected");
    public static final Status FILTERED = new Status(3, "Filtered");
    public static final Status COMPLETED = new Status(4, "Completed");

    private int id;
    private String name;
}
