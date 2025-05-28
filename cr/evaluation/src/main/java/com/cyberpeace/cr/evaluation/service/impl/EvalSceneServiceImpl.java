/**
 * 这段代码定义了一个名为 `EvalSceneServiceImpl` 的服务类，实现了 `EvalSceneService` 接口。该类主要用于处理与评估场景相关的业务逻辑。下面是对代码的简要分析： 
 * ### 类及方法概述 
 * - **类名**: `EvalSceneServiceImpl` 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **实现接口**: `EvalSceneService` 
 * ### 主要成员变量 
 * - **`localSceneService`**: 通过 `@Autowired` 注入的 `LocalSceneService` 实例，用于与本地场景相关的操作。 
 * ### 主要方法 
 * 1. **`viewScene`** 
 * - **功能**: 根据给定的中台场景ID (`mpSceneId`) 或拓扑ID (`topoId`) 返回一个 `SceneViewContext` 对象。 
 * - **参数**: 
 * - `mpSceneId`: 中台场景ID 
 * - `topoId`: 拓扑ID 
 * - **返回值**: `SceneViewContext` 对象，包含场景数据或拓扑数据。 
 * - **处理流程**: 
 * - 如果 `mpSceneId` 不为空，则尝试从 `localSceneService` 获取场景数据并设置到 `ret` 对象中。 
 * - 如果 `topoId` 不为空，则尝试从 `localSceneService` 获取拓扑数据并设置到 `ret` 对象中。 
 * - 如果两者都为空，则返回 `null`。 
 * 2. **`stopScene`** 
 * - **功能**: 停止指定的本地场景。 
 * - **参数**: 
 * - `localScene`: 需要停止的本地场景对象。 
 * - **返回值**: `Boolean` 类型，表示是否成功停止场景。 
 * - **处理流程**: 
 * - 调用 `localSceneService` 的 `stopScene` 方法停止场景。 
 * - 如果停止失败，则记录错误日志并抛出 `BusinessException` 异常。 
 * - 如果成功，则返回 `true`。 
 * 3. **`getElementFlowListContext`** 
 * - **功能**: 根据给定的查询条件和评估任务生成一个 `ElementFlowListContext` 对象。 
 * - **参数**: 
 * - `evalTaskFlowListQuery`: 查询条件对象。 
 * - `evalTask`: 评估任务对象。 
 * - **返回值**: `ElementFlowListContext` 对象，包含查询条件、分页信息等。 
 * - **处理流程**: 
 * - 将查询条件转换为分页参数 (`Page`)。 
 * - 使用 `BeanUtils.copy` 方法将查询条件复制到 `ElementFlowListContext` 对象中，并设置分页参数、中台场景ID 和 拓扑ID。 
 * ### 总结 
 * - `EvalSceneServiceImpl` 类主要处理与评估场景相关的业务逻辑，包括查看场景、停止场景和获取元素流列表上下文。 
 * - 依赖于 `LocalSceneService` 进行具体的场景操作。 
 * - 通过 `UserInfoContext` 获取当前用户ID，以确保操作的安全性和准确性。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

/**
 * 评估场景Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class EvalSceneServiceImpl implements EvalSceneService {

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 视图场景
	 *
	 * @param mpSceneId 中台场景id
	 * @param topoId    拓扑id
	 * @return {@code SceneViewContext }
	 */
	@Override
	public SceneViewContext viewScene(String mpSceneId, String topoId) {

		SceneViewContext ret = new SceneViewContext();
		String currentUserId = UserInfoContext.getUserContext().getUserId();

		// 尝试获取启动的远程场景
		if (mpSceneId != null) {
			JSONObject sceneData = localSceneService.getSceneData(mpSceneId, currentUserId);
			if (sceneData != null) {
				ret.setSceneData(sceneData);
				ret.setMpSceneId(mpSceneId);
				return ret;
			}
		}

		// 获取拓扑结构
		if (topoId != null) {
			JSONObject topoData = localSceneService.getTopoData(topoId)
					.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
			ret.setTopoId(topoId);
			ret.setTopoData(topoData);
			return ret;

		}

		return null;
	}

	/**
	 * 停场景
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopScene(LocalScene localScene) {

		Boolean stopResult = localSceneService.stopScene(localScene);

		// 停止场景失败时的处理
		if (!stopResult) {
			log.error("Failed to stop scene! localScene = [{}]", JsonUtil.toJsonString(localScene));
			throw new BusinessException(ErrorCode.FAILED_TO_STOP_SCENE);
		}

		return true;
	}

	/**
	 * 获取元素流列表上下文
	 *
	 * @param evalTaskFlowListQuery 评估任务流列表查询
	 * @param evalTask              评估任务
	 * @return {@code ElementFlowListContext }
	 */
	@Override
	public ElementFlowListContext getElementFlowListContext(
			EvalTaskFlowListQuery evalTaskFlowListQuery, EvalTask evalTask) {
		Page page = evalTaskFlowListQuery.toPageParam();

		ElementFlowListContext context = BeanUtils.copy(evalTaskFlowListQuery,
				ElementFlowListContext.class);
		context.setPage(page);
		context.setMpSceneId(evalTask.getMpSceneId());
		context.setTopoId(evalTask.getCopyedTopoId());
		return context;
	}

}
