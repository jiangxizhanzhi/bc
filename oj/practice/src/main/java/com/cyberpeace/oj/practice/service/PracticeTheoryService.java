package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

public interface PracticeTheoryService extends IService<PracticeTheory> {

	/**
	 * 分页查询理论基础练习题
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 */
	IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 用户根据编号查询题目详细信息
	 * @param resourceId
	 * @param userId
	 * @return
	 */
	WebTheoryBo selectById(String resourceId, String userId);

	/**
	 * 答案提交评估
	 * @param userId
	 * @param theoryId
	 * @param userAnswer
	 * @return
	 */
	EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer);

	/**
	 * 理论基础习题新增
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 */
	Boolean create(PracticeTheory practiceTheory, AuthContext authContext);

	/**
	 * 理论基础习题修改
	 * @param practiceTheory
	 * @param authContext
	 * @return
	 */
	int updateTheory(PracticeTheory practiceTheory, AuthContext authContext);
}
