package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.bo.OperationBo;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;

public interface PracticeOperationService extends IService<PracticeOperation> {

	/**
	 * 实践操作习题新增
	 * @param practiceOperation
	 * @param ojSceneConfig
	 * @return
	 */
	Integer addPracticeOperation(PracticeOperation practiceOperation, String ojSceneConfig);

	/**
	 * 实践操作习题修改
	 * @param updateOperation
	 * @param ojSceneConfig
	 * @return
	 */
	Integer updateOperation(PracticeOperation updateOperation, String ojSceneConfig);

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
}
