package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.entity.PracticeBase;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

public interface PracticeEvaluateHandler {

	/**
	 * 习题信息校验
	 * @param userId
	 * @param practiceBase 具体的习题数据
	 * @param userAnswer 用户提交答案
	 * @return
	 */
	EvaluateResult evaluateByBusiness(String userId, PracticeBase practiceBase, String userAnswer);

}
