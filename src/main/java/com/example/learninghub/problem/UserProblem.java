package com.example.learninghub.problem;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserProblem extends Problem {

    private final Status status;

    public UserProblem(Problem problem, Status status) {
        super(problem);
        this.status = status;
    }
}
