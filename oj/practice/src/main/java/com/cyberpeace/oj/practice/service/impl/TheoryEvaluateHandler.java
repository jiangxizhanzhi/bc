package com.cyberpeace.oj.practice.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.practice.entity.PracticeBase;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeEvaluateService;
import com.cyberpeace.oj.practice.service.bo.BlankAnswer;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 理论评估处理器
 *
 * @author SN
 * @date 2024/11/14
 */
@Service("theory")
public class TheoryEvaluateHandler implements PracticeEvaluateHandler {

	/**
	 * 评估映射
	 */
	private static Map<Integer, PracticeEvaluateService> evaluateMap = Maps.newHashMap();

	static {
		evaluateMap.put(PracticeTheory.TheoryType.SINGLE.getCode(), new SingleEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.MULTIPLE.getCode(), new MultipleEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.JUDGMENT.getCode(), new JudgmentEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.FILL_BLANK.getCode(), new BlankEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.SHORT_ANSWER.getCode(),
				new ShortAnswerEvaluate());
	}

	/**
	 * 评估由业务
	 *
	 * @param userId       用户id
	 * @param practiceBase 练习基础
	 * @param userAnswer   用户答案
	 * @return {@code EvaluateResult }
	 */
	@Override
	public EvaluateResult evaluateByBusiness(String userId, PracticeBase practiceBase,
			String userAnswer) {

		PracticeTheory practiceTheory = (PracticeTheory) practiceBase;

		// 获取对应评估示例进行评估
		PracticeEvaluateService evaluateService = evaluateMap.get(practiceTheory.getTheoryType());
		EvaluateResult evaluateResult = evaluateService.evaluate(userAnswer,
				practiceTheory.getAnswer(), practiceTheory.getScore());
		evaluateResult.setResourceId(practiceBase.getResourceId());

		// 返回评估结果
		return evaluateResult;
	}

	/**
	 * 单选习题评估
	 */
	private static class SingleEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

			if (StringUtils.isBlank(userAnswer) || StringUtils.isBlank(realAnswer)) {
				return EvaluateResult.builder().solved(CommonConstant.FALSE)
						.score(CommonConstant.ZERO.doubleValue()).totalScore(totalScore).build();
			}

			Boolean solved = userAnswer.equals(realAnswer);
			Double score = solved ? totalScore : CommonConstant.ZERO.doubleValue();
			return EvaluateResult.builder().solved(solved).score(score).totalScore(totalScore)
					.build();
		}
	}

	/**
	 * 多选习题评估
	 */
	private static class MultipleEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

			if (StringUtils.isBlank(userAnswer) || StringUtils.isBlank(realAnswer)) {
				return EvaluateResult.builder().solved(CommonConstant.FALSE)
						.score(CommonConstant.ZERO.doubleValue()).totalScore(totalScore).build();
			}

			// 处理多选答案
			List<String> userAnswerList = Arrays.asList(userAnswer.split("\\|"));
			Collections.sort(userAnswerList);
			userAnswer = StringUtils.join(userAnswerList, "");

			List<String> realAnswerList = Arrays.asList(realAnswer.split("\\|"));
			Collections.sort(realAnswerList);
			realAnswer = StringUtils.join(realAnswerList, "");

			Boolean solved = userAnswer.equals(realAnswer);
			Double score = solved ? totalScore : CommonConstant.ZERO.doubleValue();
			return EvaluateResult.builder().solved(solved).score(score).totalScore(totalScore)
					.build();

		}
	}

	/**
	 * 判断习题评估
	 */
	private static class JudgmentEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double score) {
			return evaluateMap.get(PracticeTheory.TheoryType.SINGLE.getCode()).evaluate(userAnswer,
					realAnswer, score);
		}
	}

	/**
	 * 填空习题评估
	 */
	private static class BlankEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

			if (StringUtils.isBlank(userAnswer) || StringUtils.isBlank(realAnswer)) {
				return EvaluateResult.builder().solved(CommonConstant.FALSE).totalScore(totalScore)
						.score(CommonConstant.ZERO.doubleValue()).build();
			}

			Map<String, String> submitAnswer = JsonUtil.toJsonObject(userAnswer, HashMap.class);

			List<BlankAnswer> correctAnswer = JsonUtil.toJsonObjectArray(realAnswer,
					BlankAnswer.class);
			if (submitAnswer.isEmpty() || CollectionUtils.isEmpty(correctAnswer)) {
				return EvaluateResult.builder().solved(CommonConstant.FALSE)
						.score(CommonConstant.ZERO.doubleValue()).totalScore(totalScore).build();
			}

			List<String> submits = Lists.newArrayList(submitAnswer.values());

			boolean solved = true;
			Double score = CommonConstant.ZERO.doubleValue();
			for (int i = 0; i < correctAnswer.size(); i++) {
				if (correctAnswer.get(i).getValue().equals(submits.get(i))) {
					score += MathUtils.divide(correctAnswer.get(i).getWeight() * totalScore,
							CommonConstant.HUNDRED.doubleValue(), CommonConstant.ONE);
				} else {
					solved = false;
				}
			}

			return EvaluateResult.builder().solved(solved).score(score).totalScore(totalScore)
					.build();
		}
	}

	/**
	 * 简答习题评估
	 */
	private static class ShortAnswerEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

			return EvaluateResult.builder().solved(CommonConstant.TRUE)
					.score(CommonConstant.ZERO.doubleValue()).totalScore(totalScore).build();
		}
	}

}
