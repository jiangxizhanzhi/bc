package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

public interface PracticeEvaluateService {


    /**
     * 习题评估 获取结果和得分
     * @param userAnswer
     * @param realAnswer
     * @param totalScore
     * @return
     */
    EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore);

}
