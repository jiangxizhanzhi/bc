package com.cyberpeace.oj.practice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalOperationCountBo;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 练习实操Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/04
 * @see IService
 * @since 1.0.0
 */
public interface PracticeOperationService extends IService<PracticeOperation> {

	/**
	 * 实践操作习题新增
	 *
	 * @param practiceOperation
	 * @param targetConfig
	 * @param authContext       auth 上下文
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see AuthContext
	 * @see Integer
	 */
	Integer addPracticeOperation(PracticeOperation practiceOperation, String targetConfig,
			AuthContext authContext);

	/**
	 * 实践操作习题修改
	 *
	 * @param updateOperation
	 * @param targetJson
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Integer
	 */
	Integer updateOperation(PracticeOperation updateOperation, String targetJson);

	/**
	 * 根据id查询实践操作信息
	 *
	 * @param resourceId
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see OperationBo
	 */
	OperationBo selectById(String resourceId, AuthContext authContext);

	/**
	 * 前台查询习题详细信息
	 *
	 * @param resourceId
	 * @param authContext 后台数据查询不需要
	 * @param isCollected 是否收集
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 * @see WebOperationBo
	 */
	WebOperationBo selectById(String resourceId, AuthContext authContext, Boolean isCollected);


	/**
	 * 查询由id
	 *
	 * @param resourceId 资源id
	 * @return {@code WebOperationBo }
	 * @throws
	 * @see String
	 * @see WebOperationBo
	 */
	WebOperationBo selectById(String resourceId);

	/**
	 * 分页查询实操习题
	 *
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 * @throws
	 * @see Page
	 * @see OperationListQueryBo
	 * @see AuthContext
	 * @see IPage<AdminOperationListBo>
	 */
	IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 分页由查询
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<AdminOperationListBo> }
	 * @throws
	 * @see Page
	 * @see OperationListQueryBo
	 * @see IPage<AdminOperationListBo>
	 */
	IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery);

	/**
	 * 实操练习习题解答
	 *
	 * @param userId
	 * @param resourceId
	 * @param submitAnswer
	 * @param isCollected  是否收集
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see EvaluateResult
	 */
	EvaluateResult submitAnswer(String userId, String resourceId, String submitAnswer, Boolean isCollected);

	/**
	 * 获取所有的实操习题编号
	 *
	 * @param authContext
	 * @param practiceSetId 练习设置id
	 * @return
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	List<String> selectPracticeIds(AuthContext authContext, String practiceSetId);

	/**
	 * 启动场景
	 *
	 * @param resourceId
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see JSONObject
	 */
	JSONObject startScene(String resourceId, AuthContext authContext);

	/**
	 * 场景删除
	 *
	 * @param resourceId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean deleteScene(String resourceId, AuthContext authContext);

	/**
	 * 场景节点重启
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean nodeRestart(String resourceId, String nodeId, AuthContext authContext);

	/**
	 * 场景节点重建
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean nodeRecreate(String resourceId, String nodeId, AuthContext authContext);

	/**
	 * 场景暂停与恢复
	 *
	 * @param resourceId
	 * @param status
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean pauseScene(String resourceId, Integer status, AuthContext authContext);

	/**
	 * 获取拓扑场景数据信息
	 *
	 * @param operationId
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see TopoSceneDataBo
	 */
	TopoSceneDataBo getTopoScene(String operationId, AuthContext authContext);

	/**
	 * 数据批量删除
	 *
	 * @param practiceIds
	 * @param authContext auth 上下文
	 * @return
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see Integer
	 */
	Integer batchDelete(List<String> practiceIds, AuthContext authContext);

	/**
	 * 处理实操习题dump文件
	 *
	 * @param operation
	 * @param fileStorePath
	 * @param filePathMap
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Map<String, String>
	 * @see PracticeOperationDumpLoadBo
	 */
	PracticeOperationDumpLoadBo handlerDumpOperation(PracticeOperation operation,
			String fileStorePath, Map<String, String> filePathMap);

	/**
	 * 习题集数据加载
	 *
	 * @param practiceSetId
	 * @param dumpBo
	 * @param filePathMap
	 * @param filePath
	 * @param userId        用户id
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeOperationDumpLoadBo
	 * @see Map<String, String>
	 * @see String
	 * @see String
	 * @see PracticeOperation
	 */
	PracticeOperation loadPracticeOperation(String practiceSetId,
			PracticeOperationDumpLoadBo dumpBo, Map<String, String> filePathMap, String filePath,
			String userId);

	/**
	 * 创建远程操作日志
	 *
	 * @param practiceId
	 * @param logContent 日志内容
	 * @throws
	 * @see String
	 * @see LogContent
	 */
	void createMpLog(String practiceId, LogContent logContent);

	/**
	 * 查询要数据导出的习题
	 *
	 * @param setIds
	 * @param practiceId 练习id
	 * @return {@code List<PracticeOperation> }
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see List<PracticeOperation>
	 */
	List<PracticeOperation> selectExportPractice(List<String> setIds, String practiceId);

	/**
	 * 实操习题数据导出
	 *
	 * @param setPath
	 * @param practiceOperations 练习操作
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see List<PracticeOperation>
	 * @see Boolean
	 */
	void exportPractice(String setPath, List<PracticeOperation> practiceOperations, Boolean disableImageExport);

	/**
	 * 获取实操习题附件
	 *
	 * @param attachmentId
	 * @return
	 * @throws
	 * @see String
	 * @see List<PracticeOperationFileBo>
	 */
	List<PracticeOperationFileBo> getOperationFile(String attachmentId);

	/**
	 * 计算各类型习题数量
	 *
	 * @param authContext
	 * @param listQueryBo
	 * @return
	 * @throws
	 * @see AuthContext
	 * @see OperationListQueryBo
	 * @see ExternalOperationCountBo
	 */
	ExternalOperationCountBo typeCount(AuthContext authContext, OperationListQueryBo listQueryBo);

	/**
	 * 获取实操题练习进度
	 *
	 * @param practiceSetId
	 * @return
	 * @throws
	 * @see String
	 * @see ProgressStatisticsRes
	 */
	ProgressStatisticsRes getProgressStatistics(String practiceSetId);

	/**
	 * 根据用户和习题集查询所有可以看见的实操题id
	 *
	 * @param authContext
	 * @param practiceSetId
	 * @return
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	List<String> getOptionIdsByAuth(AuthContext authContext, String practiceSetId);

	/**
	 * 根据用户和起止时间获取实操题解出记录
	 *
	 * @param userInfo
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws
	 * @see UserInfo
	 * @see Date
	 * @see Date
	 * @see List<OperationRecordRes>
	 */
	List<OperationRecordRes> getOperationRecord(UserInfo userInfo, Date startTime, Date endTime);

	/**
	 * 获取各方向的解题进度
	 *
	 * @return
	 * @throws
	 * @see List<CategoryProgressRes>
	 */
	List<CategoryProgressRes> getOperationCategoryProgress();

	/**
	 * 再练一次状态的提交
	 *
	 * @param userId
	 * @param resourceId
	 * @param submitAnswer
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see EvaluateResult
	 */
	EvaluateResult submitAnswerAgain(String userId, String resourceId, String submitAnswer);

	/**
	 * 实操题方向和习题集
	 *
	 * @return
	 * @throws
	 * @see List<PracticeOperation>
	 */
	List<PracticeOperation> getCategoryIdAndSetIds();

	/**
	 * 获取实操由博 德id列表
	 *
	 * @param nodeIds 节点id列表
	 * @return {@code List<AdminOperationListBo> }
	 * @throws
	 * @see List<String>
	 * @see List<AdminOperationListBo>
	 */
	List<AdminOperationListBo> getOperationByBodeIds(List<String> nodeIds);

	/**
	 * 导出批量
	 *
	 * @param ids id列表
	 * @return {@code File }
	 * @throws
	 * @see List<String>
	 * @see File
	 */
	File dumpBatch(List<String> ids);

	/**
	 * 加载批量
	 *
	 * @param realPath 真实路径
	 * @param userId   用户id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean loadBatch(String realPath, String userId);

	/**
	 * 导出批量简洁
	 *
	 * @param ids id列表
	 * @return {@code File }
	 * @throws
	 * @see List<String>
	 * @see File
	 */
	File dumpBatchSlim(List<String> ids);

	/**
	 * 加载批量简洁
	 *
	 * @param realPath 真实路径
	 * @param userId   用户id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean loadBatchSlim(String realPath, String userId, Boolean other);
}
