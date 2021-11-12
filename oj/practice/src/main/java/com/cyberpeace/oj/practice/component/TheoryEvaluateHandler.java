package com.cyberpeace.oj.practice.component;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeEvaluateService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.google.common.collect.Maps;

import lombok.Data;

public class TheoryEvaluateHandler extends PracticeEvaluateHandler {

	private static Map<Integer, PracticeEvaluateService> evaluateMap = Maps.newHashMap();

	static {
		evaluateMap.put(PracticeTheory.TheoryType.SINGLE.getCode(), new SingleEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.MULTIPLE.getCode(), new MultipleEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.JUDGMENT.getCode(), new JudgmentEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.FILL_BLANK.getCode(), new BlankEvaluate());
		evaluateMap.put(PracticeTheory.TheoryType.SHORT_ANSWER.getCode(),
				new ShortAnswerEvaluate());
	}

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Override
	EvaluateResult evaluatePracticeId(String practiceId, String userAnswer) {

		// 判断是否已完成答题
		PracticeSolved solvedPractice = practiceSolvedMapper
				.selectUserAndId(UserInfoContext.getUserContext().getUserId(), practiceId);
		if (solvedPractice.getSolved()) {
			return BeanUtils.copy(solvedPractice, EvaluateResult.class);
		}

		// 判断习题是否存在
		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(practiceId);
		if (null == practiceTheory) {
			throw new BusinessException(OjBusinessErrorCode.THEORY_NOT_FIND);
		}

		// 获取对应评估示例进行评估
		PracticeEvaluateService evaluateService = evaluateMap.get(practiceTheory.getTheoryType());
		EvaluateResult evaluateResult = evaluateService.evaluate(userAnswer,
				practiceTheory.getAnswer(), practiceTheory.getScore());

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(practiceId);
		practiceSolved.setSubmitUserId(UserInfoContext.getUserContext().getUserId());
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());

		practiceSolved.setCollection(resourceCollectionService.selectByUnique(
				UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode(), practiceId));
		practiceSolvedMapper.insert(practiceSolved);

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
				return new EvaluateResult(CommonConstant.FALSE, CommonConstant.ZERO.doubleValue(),
						totalScore);
			}
			Boolean solved = userAnswer.equals(realAnswer);
			Double score = solved ? totalScore : CommonConstant.ZERO.doubleValue();
			return new EvaluateResult(solved, score, totalScore);

		}
	}

	/**
	 * 多选习题评估
	 */
	private static class MultipleEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double score) {

			return evaluateMap.get(PracticeTheory.TheoryType.SINGLE.getCode()).evaluate(userAnswer,
					realAnswer, score);
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
				return new EvaluateResult(CommonConstant.FALSE, CommonConstant.ZERO.doubleValue(),
						totalScore);
			}

			Map<String, String> submitAnswer = (Map<String, String>) JsonUtil
					.toJsonObjectArray(userAnswer, Map.class);
			List<BlankAnswer> correctAnswer = JsonUtil.toJsonObjectArray(realAnswer,
					BlankAnswer.class);
			if (submitAnswer.isEmpty() || CollectionUtils.isEmpty(correctAnswer)) {
				return new EvaluateResult(CommonConstant.FALSE, CommonConstant.ZERO.doubleValue(),
						totalScore);
			}

			List<String> submits = (List<String>) submitAnswer.values();
			List<String> corrects = StreamApiUtils.columnList(correctAnswer, BlankAnswer::getValue);

			boolean solved = CollectionUtils.isEqualCollection(submits, corrects);
			Double score = CommonConstant.ZERO.doubleValue();
			for (BlankAnswer blankAnswer : correctAnswer) {
				if (submits.contains(blankAnswer.getValue())) {
					score += MathUtils.divide(blankAnswer.getWeight() * totalScore,
							CommonConstant.HUNDRED.doubleValue(), CommonConstant.ONE);
				}
			}

			return new EvaluateResult(solved, score, totalScore);
		}
	}

	/**
	 * 简答习题评估
	 */
	private static class ShortAnswerEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double score) {

			return new EvaluateResult(CommonConstant.TRUE, CommonConstant.ZERO.doubleValue(),
					score);
		}
	}

	@Data
	class BlankAnswer {

		/** 权重*/
		private Integer weight;

		/** 答案*/
		private String value;
	}

}
