package com.dynata.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the participation status of a member in a survey.
 */
@Getter
@AllArgsConstructor
public class Status {
    private int id;
    private String name;
}
