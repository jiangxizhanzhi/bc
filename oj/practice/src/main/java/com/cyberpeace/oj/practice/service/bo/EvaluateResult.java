package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvaluateResult implements Serializable {
    /**
     * 是否通过
     */
    private final Boolean isPass;

    /**
     * 得分
     */
    private final Double score;

    /**
     * 总分
     */
    private final Double totalScore;

    public EvaluateResult(Boolean isPass, Double score, Double totalScore) {
        this.isPass = isPass;
        this.score = score;
        this.totalScore = totalScore;
    }
}
