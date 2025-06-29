package com.dynata.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a member who can participate in surveys.
 */
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Member {
    private int id;
    private String fullname;
    private String email;
    private boolean active;
}
