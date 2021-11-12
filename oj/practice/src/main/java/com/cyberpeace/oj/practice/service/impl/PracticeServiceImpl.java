package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.practice.constant.PracticeType;
import com.cyberpeace.oj.practice.constant.TheoryQuestionType;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeServiceMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    PracticeServiceMapper practiceServiceMapper;

    @Autowired
    PracticeTheoryMapper practiceTheoryMapper;

    @Autowired
    PracticeOperationMapper practiceOperationMapper;

    @Override
    public EvaluateResult evaluator(String resourceId, Integer practiceType, String answer) {
        return evaluator(resourceId, practiceType, answer, "");
    }

    @Override
    public EvaluateResult evaluator(String resourceId, Integer practiceType, String answer, String user) {

        // 针对同一用户、同一练习, 仅评估一次  TODO 试卷评估等不同形式待处理
        if (!user.isEmpty()) {
            EvaluateResult result = practiceServiceMapper.getSolved(user, resourceId);
            if (result != null) {
                return result;
            }
        }

        if (practiceType.equals(PracticeType.THEORY.getCode())) {
            PracticeTheory practiceObject = practiceTheoryMapper.selectById(resourceId);
            return evaluator(practiceObject, answer, user);

        } else if (practiceType.equals(PracticeType.OPERATION.getCode())) {
            PracticeOperation practiceObject = practiceOperationMapper.selectById(resourceId);
            return evaluator(practiceObject, answer, user);

        } else {
            throw new IllegalArgumentException("Type not available.");
        }
    }

    @Override
    public <P extends PracticeBase> EvaluateResult evaluator(P practiceObject, String answer) {
        return evaluator(practiceObject, answer, "");
    }

    @Override
    public <P extends PracticeBase> EvaluateResult evaluator(P practiceObject, String answer, String user) {

        EvaluateResult result;

        if (practiceObject instanceof PracticeTheory) {
            PracticeTheory origin = (PracticeTheory) practiceObject;
            if (origin.getTheoryType().equals(TheoryQuestionType.SINGLE.getValue())) {
                result = Evaluator.singleEval((PracticeTheory) practiceObject, answer);

            } else if (origin.getTheoryType().equals(TheoryQuestionType.MULTIPLE.getValue())) {
                result = Evaluator.multipleEval((PracticeTheory) practiceObject, answer);

            } else if (origin.getTheoryType().equals(TheoryQuestionType.JUDGMENT.getValue())) {
                result = Evaluator.judgmentEval((PracticeTheory) practiceObject, answer);

            } else if (origin.getTheoryType().equals(TheoryQuestionType.FILL_BLANK.getValue())) {
                result = Evaluator.fillBlankEval((PracticeTheory) practiceObject, answer);

            } else if (origin.getTheoryType().equals(TheoryQuestionType.SHORT_ANSWER.getValue())) {
                result = Evaluator.shortAnswerEval((PracticeTheory) practiceObject, answer);

            } else {
                throw new IllegalArgumentException("Question type invalid.");
            }
        } else if (practiceObject instanceof PracticeOperation) {
            result = Evaluator.operationEval((PracticeOperation) practiceObject, answer);

        } else {
            throw new IllegalArgumentException("Type not available.");
        }

        // 通过记录写入DB
        if (!user.isEmpty() && result.getSolved()) {
            practiceServiceMapper.saveSolved(user, answer, result, practiceObject, new Date());
        }

        return result;
    }

    @Override
    public Paper createTestPaper(List<String> resourceIds) {
        Paper object = new Paper();

        List<PracticeTheory> theoryList = practiceTheoryMapper.selectBatchIds(resourceIds);
        List<PracticeOperation> operationList = practiceOperationMapper.selectBatchIds(resourceIds);

//        for (PracticeTheory theory:
//             theoryList) {
//            theory
//        }
        return null;
    }
}

class Evaluator {

    // TODO 1.需要重载, 兼顾其它入参? 2.支持 Practice*Ref 类型
    /**
     * 单选题评估
     */
    public static EvaluateResult singleEval(PracticeTheory practice, String answer) {
        Boolean isPass = practice.getAnswer().equalsIgnoreCase(answer);
        Double score = isPass ? practice.getScore() : 0;
        return new EvaluateResult(isPass, score, practice.getScore());
    }

    /**
     * 多选题评估
     */
    public static EvaluateResult multipleEval(PracticeTheory practice, String answer) {
        return singleEval(practice, answer);
    }

    /**
     * 判断题评估
     */
    public static EvaluateResult judgmentEval(PracticeTheory practice, String answer) {
        return singleEval(practice, answer);
    }

    /**
     * 填空题评估
     */
    public static EvaluateResult fillBlankEval(PracticeTheory practice, String answer) {
        Double score = 0d;
        boolean isPass = false;

        JSONObject jsonAnswer = JSONObject.parseObject(answer);
        JSONArray blankArray = JSONArray.parseArray(practice.getAnswer());

        for (int i = 0; i < blankArray.size(); i++) {
            // 获取用户提交的每个填空答案
            String v = jsonAnswer.getString(Integer.toString(i));
            if (v == null) {
                continue;
            }

            JSONObject blank = (JSONObject) blankArray.get(i);
            if (blank.getString("value").equalsIgnoreCase(v)) {

                Integer blankWeight = blank.getInteger("weight");
                BigDecimal proportion = new BigDecimal(practice.getScore() * blankWeight * 0.01f);
                score = score + proportion.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

                // 答对一项视为通过
                isPass = true;
            }
        }

        return new EvaluateResult(isPass, score, practice.getScore());
    }

    /**
     * 简答题评估, 默认通过, 不设置得分.
     */
    public static EvaluateResult shortAnswerEval(PracticeTheory practice, String answer) {
        return new EvaluateResult(true, 0d, practice.getScore());
    }

    /**
     * 操作题评估
     */
    // TODO 处理待定
    public static EvaluateResult operationEval(PracticeOperation practice, String answer) {
        return new EvaluateResult(true, 0d, practice.getScore());
    }
}
