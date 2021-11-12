package com.cyberpeace.oj.practice.component;

import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeEvaluateService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class OperationEvaluateHandler extends PracticeEvaluateHandler {

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Override
	EvaluateResult evaluatePracticeId(String practiceId, String userAnswer) {

		PracticeOperation practiceOperation = practiceOperationMapper.selectById(practiceId);
		if (StringUtils.isNotBlank(practiceOperation.getTopoId())) {

		}
		return null;
	}

	private static class OperationEvaluate implements PracticeEvaluateService {

		@Override
		public EvaluateResult evaluate(String userAnswer, String realAnswer, Double totalScore) {

			return null;
		}
	}
}
