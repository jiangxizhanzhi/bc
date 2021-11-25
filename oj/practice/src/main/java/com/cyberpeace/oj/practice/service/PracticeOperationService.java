package com.cyberpeace.oj.practice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.bo.*;

import java.util.List;

public interface PracticeOperationService extends IService<PracticeOperation> {

	/**
	 * 实践操作习题新增
	 * @param practiceOperation
	 * @param ojSceneConfig
	 * @return
	 */
	Integer addPracticeOperation(PracticeOperation practiceOperation, JSONObject ojSceneConfig,
			AuthContext authContext);

	/**
	 * 实践操作习题修改
	 * @param updateOperation
	 * @param ojSceneConfig
	 * @return
	 */
	Integer updateOperation(PracticeOperation updateOperation, JSONObject ojSceneConfig);

	/**
	 * 根据id查询实践操作信息
	 * @param resourceId
	 * @return
	 */
	OperationBo selectById(String resourceId);

	/**
	 * 前台查询习题详细信息
	 * @param resourceId
	 * @param userId 后台数据查询不需要
	 * @return
	 */
	WebOperationBo selectById(String resourceId, String userId);

	/**
	 * 分页查询实操习题
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 */
	IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 实操练习习题解答
	 * @param userId
	 * @param resourceId
	 * @param submitAnswer
	 * @return
	 */
	EvaluateResult submitAnswer(String userId, String resourceId, String submitAnswer);

	/**
	 * 获取所有的实操习题编号
	 * @param authContext
	 * @return
	 */
    List<String> selectPracticeIds(AuthContext authContext);
}
