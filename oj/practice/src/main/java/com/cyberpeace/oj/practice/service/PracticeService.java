package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.dao.entity.PracticeBase;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import java.util.List;

public interface PracticeService {

    /**
     * 答题评估
     *
     * @param practiceObject `PracticeBase`的子类型对象
     * @param answer         用户提交答案, 一定为String
     * @param user           非必填, 未填情况下, 仅评估, 不会为其生成评估记录
     * @param <P>            基于`PracticeBase`class类型
     * @return EvaluateResult 评估结果信息, 包含是否通过、得分、总分.
     */
    <P extends PracticeBase> EvaluateResult evaluator(P practiceObject, String answer, String user);

    <P extends PracticeBase> EvaluateResult evaluator(P practiceObject, String answer);

    EvaluateResult evaluator(String resourceId, Integer practiceType, String answer);

    EvaluateResult evaluator(String resourceId, Integer practiceType, String answer, String user);

    Paper createTestPaper(List<String> resourceIds);

}

