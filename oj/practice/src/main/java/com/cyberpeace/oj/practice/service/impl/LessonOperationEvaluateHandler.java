package com.cyberpeace.oj.practice.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

@Service("lesson_operation")
public class LessonOperationEvaluateHandler {

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	public EvaluateResult evaluateByBusiness(String userId, PracticeOperation operation,
			String userAnswer, String lessonId) {

		// 获取正确答案
		String correctAnswer = correctAnswer(userId, operation, lessonId);
		if (StringUtils.isBlank(correctAnswer)) {
			return new EvaluateResult(CommonConstant.FALSE, CommonConstant.ZERO.doubleValue(),
					operation.getScore(), operation.getResourceId());
		}

		// 答案评估
		EvaluateResult evaluateResult = evaluate(userAnswer, correctAnswer, operation.getScore());
		evaluateResult.setResourceId(operation.getResourceId());

		return evaluateResult;
	}

	/**
	 * 获取正确答案
	 * @param userId
	 * @param practiceOperation
	 * @return
	 */
	private String correctAnswer(String userId, PracticeOperation practiceOperation,
			String lessonId) {

		// 非动态环境 返回数据库答案
		if (!practiceOperation.getDynamicEnv()
				&& StringUtils.isBlank(practiceOperation.getTopoId())) {
			return practiceOperation.getAnswer();
		}

		// 获取业务编号用于查询场景信息
		String businessId = lessonId + CommonConstant.CODE_COMMA
				+ practiceOperation.getResourceId();

		// 查询场景信息
		OjLocalScene ojLocalScene = practiceOperation.getSceneShareMode()
				.intValue() == SceneShare.SHARED.getCode().intValue()
						? ojLocalSceneMapper.selectPublicScene(businessId, CommonConstant.TRUE,
								CommonConstant.TRUE)
						: ojLocalSceneMapper.selectPrivateScene(userId, businessId,
								CommonConstant.TRUE);
		return null != ojLocalScene ? ojLocalScene.getFlags() : "";

	}

	/**
	 * 评估成绩
	 * @param userAnswer
	 * @param realAnswer
	 * @param totalScore
	 * @return
	 */
	private EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

		List<String> flags = JsonUtil.toJsonObjectArray(realAnswer, String.class);
		return EvaluateResult.builder().solved(flags.contains(userAnswer)).totalScore(totalScore)
				.score(flags.contains(userAnswer) ? totalScore : CommonConstant.ZERO.doubleValue())
				.build();
	}

}
