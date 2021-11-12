package com.cyberpeace.oj.practice.component;

import java.util.Map;

import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.google.common.collect.Maps;

import lombok.Getter;

@Component
public abstract class PracticeEvaluateHandler {

	private static Map<Integer, PracticeEvaluateHandler> handlerMap = Maps.newHashMap();

	static {
		handlerMap.put(EvaluateType.THEORY.getCode(), new TheoryEvaluateHandler());
		handlerMap.put(EvaluateType.OPERATION.getCode(), new OperationEvaluateHandler());
		handlerMap.put(EvaluateType.LESSON.getCode(), new LessonPracticeHandler());
	}

	/**
	 * 习题数据校验
	 * @param evaluateType
	 * @param userAnswer
	 * @param practiceId
	 * @return
	 */
	public static EvaluateResult evaluate(EvaluateType evaluateType, String practiceId,
			String userAnswer) {

		PracticeEvaluateHandler practiceEvaluateHandler = handlerMap.get(evaluateType);

		EvaluateResult evaluateResult = practiceEvaluateHandler.evaluatePracticeId(practiceId,
				userAnswer);

		return evaluateResult;
	}

	abstract EvaluateResult evaluatePracticeId(String practiceId, String userAnswer);

	/**
	 * 需要进行习题评估的类型
	 */
	public enum EvaluateType implements GenericEnum {

		THEORY(0, "理论基础"), OPERATION(1, "实践操作"), LESSON(2, "课后练习"), @Deprecated
		EXAM(3, "考试");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EvaluateType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
