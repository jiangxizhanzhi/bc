package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.entity.PracticeRandomFlag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.practice.entity.PracticeBase;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeRandomFlagMapper;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeEvaluateService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Slf4j
@Service("operation")
public class OperationEvaluateHandler implements PracticeEvaluateHandler {

	private static Map<Integer, PracticeEvaluateService> evaluateMap = Maps.newHashMap();

	static {
		// 暂时没有别的解题方案 暂时默认key为零
		evaluateMap.put(CommonConstant.ZERO, new OperationEvaluate());
	}

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private PracticeRandomFlagMapper practiceRandomFlagMapper;

	@Override
	public EvaluateResult evaluateByBusiness(String userId, PracticeBase practiceBase,
			String userAnswer) {

		PracticeOperation operation = (PracticeOperation) practiceBase;

		String correctAnswer = correctAnswer(userId, operation);

		if (StringUtils.isBlank(correctAnswer)) {
			return new EvaluateResult(CommonConstant.FALSE, CommonConstant.ZERO.doubleValue(),
					operation.getScore(), practiceBase.getResourceId());
		}

		PracticeEvaluateService evaluateService = evaluateMap.get(CommonConstant.ZERO);
		EvaluateResult evaluateResult = evaluateService.evaluate(userAnswer, correctAnswer,
				operation.getScore());
		evaluateResult.setResourceId(practiceBase.getResourceId());

		return evaluateResult;
	}

	/**
	 * 获取正确答案
	 * @param userId
	 * @param practiceOperation
	 * @return
	 */
	private String correctAnswer(String userId, PracticeOperation practiceOperation) {

		// 随机flag题目判断
		if (practiceOperation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.RANDOM
				.getCode().intValue()) {
			PracticeRandomFlag randomFlag = practiceRandomFlagMapper.selectByUserAndPractice(userId,
					practiceOperation.getResourceId(),
					PracticeRandomFlag.FlagType.PRACTICE.getCode());
			if (null == randomFlag) {
				log.error("randomFlag is null ,userId =[{}]，ResourceId=[{}]", userId, practiceOperation.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.RELOAD_PRACTICE);
			}
			return JsonUtil.arrayToString(Lists.newArrayList(randomFlag.getFlag()));
		}

		if (!practiceOperation.getDynamicEnv()) {
			return practiceOperation.getAnswer();
		}

		if (practiceOperation.getSceneShareMode().intValue() == SceneShare.SHARED.getCode()
				.intValue()) {
			OjLocalScene ojLocalScene = ojLocalSceneMapper.selectPublicScene(
					practiceOperation.getResourceId(), CommonConstant.TRUE, CommonConstant.TRUE);
			return null != ojLocalScene ? ojLocalScene.getFlags() : "";

		} else {
			OjLocalScene ojLocalScene = ojLocalSceneMapper.selectPrivateScene(userId,
					practiceOperation.getResourceId(), CommonConstant.TRUE);
			return null != ojLocalScene ? ojLocalScene.getFlags() : "";
		}
	}

	/**
	 * 习题校验
	 */
	private static class OperationEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {
			List<String> flags = JsonUtil.toJsonObjectArray(realAnswer, String.class);

			if (flags.contains(userAnswer)) {
				return EvaluateResult.builder().score(totalScore).totalScore(totalScore)
						.solved(CommonConstant.TRUE).build();
			}

			return EvaluateResult.builder().solved(CommonConstant.FALSE).totalScore(totalScore)
					.score(CommonConstant.ZERO.doubleValue()).build();
		}
	}
}
