package com.cyberpeace.oj.practice.service;

import com.cyberpeace.oj.practice.dao.entity.PracticeBase;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

/**
 * 实践评估处理器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface PracticeEvaluateHandler {

	/**
	 * 习题信息校验
	 *
	 * @param userId
	 * @param practiceBase 具体的习题数据
	 * @param userAnswer   用户提交答案
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeBase
	 * @see String
	 * @see EvaluateResult
	 */
	EvaluateResult evaluateByBusiness(String userId, PracticeBase practiceBase, String userAnswer);

}
