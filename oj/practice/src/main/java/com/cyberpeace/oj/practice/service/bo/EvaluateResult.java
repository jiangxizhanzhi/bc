package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

@Data
public class EvaluateResult {
    /**
     * 是否通过
     */
    private final Boolean isPass;

    /**
     * 得分
     */
    private final Float score;

    /**
     * 总分
     */
    private final Float totalScore;

    public EvaluateResult(Boolean isPass, Float score, Float totalScore) {
        this.isPass = isPass;
        this.score = score;
        this.totalScore = totalScore;
    }
}
