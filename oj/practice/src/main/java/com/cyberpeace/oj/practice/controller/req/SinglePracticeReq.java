package com.cyberpeace.oj.practice.controller.req;

import lombok.Data;

/**
 * TestPaperReq使用, 提交试卷中的练习信息
 */
@Data
public class SinglePracticeReq {

    private String resourceId;

    private Integer practiceType;

    private Float score;

    private Boolean isOptionRandom = Boolean.TRUE;

}
