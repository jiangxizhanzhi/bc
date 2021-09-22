package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

@Data
public class TestPaperPractice {
    private String resourceId;

    private String originId;

    private String title;

    private String content;

    private Float score;

    private String practiceType;

    private String questionType;

    private String option;

    private Boolean isRandomOption;

    private String keyword;

    private String file;

    private String fileName;

}
