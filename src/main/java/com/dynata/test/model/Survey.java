package com.dynata.test.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a survey that members can participate in.
 */
@Getter
@AllArgsConstructor
@ToString
public class Survey {
    private int id;
    private String name;
    private int expectedCompletes;
    private int completionPoints;
    private int filteredPoints;
}
