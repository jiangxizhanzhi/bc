package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

/**
 * 实践评估Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface PracticeEvaluateService {


    /**
     * 习题评估 获取结果和得分
     *
     * @param userAnswer
     * @param realAnswer
     * @param totalScore
     * @return
     * @throws
     * @see String
     * @see String
     * @see Double
     * @see EvaluateResult
     */
    EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore);

}
