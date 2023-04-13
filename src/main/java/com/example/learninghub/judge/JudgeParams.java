package com.example.learninghub.judge;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JudgeParams {
    private String code;
    //TODO: enum
    private String language;
    private int problemId;
    private int testCase;

}