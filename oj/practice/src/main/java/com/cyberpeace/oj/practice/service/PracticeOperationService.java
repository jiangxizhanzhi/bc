package com.cyberpeace.oj.practice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PatternReq;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalOperationCountBo;

/**
 * 练习实操Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface PracticeOperationService extends IService<PracticeOperation> {

	/**
	 * 实践操作习题新增
	 *
	 * @param practiceOperation
	 * @param ojSceneConfig
	 * @param authContext       auth 上下文
	 * @return
	 */
	Integer addPracticeOperation(PracticeOperation practiceOperation, JSONObject ojSceneConfig,
			AuthContext authContext);

	/**
	 * 实践操作习题修改
	 *
	 * @param updateOperation
	 * @param ojSceneConfig
	 * @return
	 */
	Integer updateOperation(PracticeOperation updateOperation, JSONObject ojSceneConfig);

	/**
	 * 根据id查询实践操作信息
	 *
	 * @param resourceId
	 * @param authContext auth 上下文
	 * @return
	 */
	OperationBo selectById(String resourceId, AuthContext authContext);

	/**
	 * 前台查询习题详细信息
	 *
	 * @param resourceId
	 * @param authContext 后台数据查询不需要
	 * @param isCollected 是否收集
	 * @return
	 */
	WebOperationBo selectById(String resourceId, AuthContext authContext, Boolean isCollected);

	/**
	 * 分页查询实操习题
	 *
	 * @param page
	 * @param listQuery
	 * @param authContext
	 * @return
	 */
	IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext);

	/**
	 * 实操练习习题解答
	 *
	 * @param userId
	 * @param resourceId
	 * @param submitAnswer
	 * @param isCollected  是否收集
	 * @return
	 */
	EvaluateResult submitAnswer(String userId, String resourceId, String submitAnswer, Boolean isCollected);

	/**
	 * 获取所有的实操习题编号
	 *
	 * @param authContext
	 * @param practiceSetId 练习设置id
	 * @return
	 */
	List<String> selectPracticeIds(AuthContext authContext, String practiceSetId);

	/**
	 * 启动场景
	 *
	 * @param resourceId
	 * @param authContext auth 上下文
	 * @return
	 */
	JSONObject startScene(String resourceId, AuthContext authContext);

	/**
	 * 场景删除
	 *
	 * @param resourceId
	 * @param authContext
	 * @return
	 */
	Boolean deleteScene(String resourceId, AuthContext authContext);

	/**
	 * 场景节点重启
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param authContext auth 上下文
	 * @return
	 */
	Boolean nodeRestart(String resourceId, String nodeId, AuthContext authContext);

	/**
	 * 场景节点重建
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param authContext
	 * @return
	 */
	Boolean nodeRecreate(String resourceId, String nodeId, AuthContext authContext);

	/**
	 * 场景暂停与恢复
	 *
	 * @param resourceId
	 * @param status
	 * @param authContext
	 * @return
	 */
	Boolean pauseScene(String resourceId, Integer status, AuthContext authContext);

	/**
	 * 获取拓扑场景数据信息
	 *
	 * @param operationId
	 * @param authContext auth 上下文
	 * @return
	 */
	TopoSceneDataBo getTopoScene(String operationId, AuthContext authContext);

	/**
	 * 数据批量删除
	 *
	 * @param practiceIds
	 * @param authContext auth 上下文
	 * @return
	 */
	Integer batchDelete(List<String> practiceIds, AuthContext authContext);

	/**
	 * 处理实操习题dump文件
	 *
	 * @param operation
	 * @param fileStorePath
	 * @param filePathMap
	 * @return
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
	 */
	PracticeOperation loadPracticeOperation(String practiceSetId,
			PracticeOperationDumpLoadBo dumpBo, Map<String, String> filePathMap, String filePath,
			String userId);

	/**
	 * 创建远程操作日志
	 *
	 * @param practiceId
	 * @param logContent 日志内容
	 */
	void createMpLog(String practiceId, LogContent logContent);

	/**
	 * 查询要数据导出的习题
	 *
	 * @param setIds
	 * @param practiceId 练习id
	 * @return {@code List<PracticeOperation> }
	 */
	List<PracticeOperation> selectExportPractice(List<String> setIds, String practiceId);

	/**
	 * 实操习题数据导出
	 *
	 * @param setPath
	 * @param practiceOperations 练习操作
	 * @param disableImageExport 禁用图像导出
	 */
	void exportPractice(String setPath, List<PracticeOperation> practiceOperations, Boolean disableImageExport);

	/**
	 * 获取实操习题附件
	 *
	 * @param attachmentId
	 * @return
	 */
	List<PracticeOperationFileBo> getOperationFile(String attachmentId);

	/**
	 * 计算各类型习题数量
	 *
	 * @param authContext
	 * @param listQueryBo
	 * @return
	 */
	ExternalOperationCountBo typeCount(AuthContext authContext, OperationListQueryBo listQueryBo);

	/**
	 * 获取实操题练习进度
	 *
	 * @param practiceSetId
	 * @return
	 */
	ProgressStatisticsRes getProgressStatistics(String practiceSetId);

	/**
	 * 根据用户和习题集查询所有可以看见的实操题id
	 *
	 * @param authContext
	 * @param practiceSetId
	 * @return
	 */
	List<String> getOptionIdsByAuth(AuthContext authContext, String practiceSetId);

	/**
	 * 根据用户和起止时间获取实操题解出记录
	 *
	 * @param userInfo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<OperationRecordRes> getOperationRecord(UserInfo userInfo, Date startTime, Date endTime);

	/**
	 * 获取各方向的解题进度
	 *
	 * @return
	 */
	List<CategoryProgressRes> getOperationCategoryProgress();

	/**
	 * 再练一次状态的提交
	 *
	 * @param userId
	 * @param resourceId
	 * @param submitAnswer
	 * @return
	 */
	EvaluateResult submitAnswerAgain(String userId, String resourceId, String submitAnswer);

	/**
	 * 实操题方向和习题集
	 *
	 * @return
	 */
	List<PracticeOperation> getCategoryIdAndSetIds();

	/**
	 * 获取知识点列表并通过频率排序
	 *
	 * @param pattern 模糊匹配
	 * @param isWeb   前后台
	 * @return {@code List<String>}
	 */
	List<String> getKnowledgeListByFrequency(PatternReq pattern, Boolean isWeb);
}
