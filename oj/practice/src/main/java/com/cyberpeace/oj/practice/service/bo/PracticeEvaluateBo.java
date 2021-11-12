package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeEvaluateBo implements Serializable {

    /** 是否解决*/
    private Boolean solved;

    /** 用户得分*/
    private Double score;

    /** 总分*/
    private Double totalScore;

}
