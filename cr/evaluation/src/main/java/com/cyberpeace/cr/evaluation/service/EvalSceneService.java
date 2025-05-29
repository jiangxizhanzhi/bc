package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 测试靶场-场景管理
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSceneService` 的接口，该接口用于处理与测试靶场中的场景管理相关的操作。以下是详细的分析和总结： 
 * ### 接口名称 
 * - `EvalSceneService` 
 * ### 包名 
 * - `com.cyberpeace.cr.evaluation.service` 
 * ### 导入的类和接口 
 * - `com.cyberpeace.cr.crbase.service.context.SceneViewContext`: 用于表示场景视图上下文。 
 * - `com.cyberpeace.cr.crbase.service.context.scene.ElementFlowListContext`: 用于表示元素流量列表上下文。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalTask`: 表示评估任务实体。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery`: 用于构造评估任务流量列表查询参数。 
 * - `com.cyberpeace.cs.dao.entity.LocalScene`: 表示本地场景实体。 
 * ### 方法定义 
 * 1. **`viewScene(String mpSceneId, String topoId)`** 
 * - **功能**: 查看指定场景。 
 * - **参数**: 
 * - `mpSceneId`: 场景ID。 
 * - `topoId`: 拓扑ID。 
 * - **返回值**: `SceneViewContext` 对象，表示场景视图上下文。 
 * 2. **`stopScene(LocalScene localScene)`** 
 * - **功能**: 关闭指定的场景。 
 * - **参数**: 
 * - `localScene`: 本地场景对象。 
 * - **返回值**: `Boolean` 类型，表示操作是否成功。 
 * 3. **`getElementFlowListContext(EvalTaskFlowListQuery evalTaskFlowListQuery, EvalTask evalTask)`** 
 * - **功能**: 构造场景流量列表查询参数。 
 * - **参数**: 
 * - `evalTaskFlowListQuery`: 评估任务流量列表查询参数对象。 
 * - `evalTask`: 评估任务对象。 
 * - **返回值**: `ElementFlowListContext` 对象，表示元素流量列表上下文。 
 * ### 总结 
 * 这个接口主要用于处理与测试靶场中的场景管理相关的一系列操作，包括查看场景、关闭场景以及构造场景流量列表查询参数。通过这些方法，可以实现对场景的控制和管理。 
**/
public interface EvalSceneService {

	/**
	 * 查看场景
	 *
	 * @param mpSceneId
	 * @param topoId
	 * @return
	 */
	SceneViewContext viewScene(String mpSceneId, String topoId);

	/**
	 * 关闭场景
	 *
	 * @param localScene
	 * @return
	 */
	Boolean stopScene(LocalScene localScene);

	/**
	 * 构造场景流量列表查询参数
	 *
	 * @param evalTaskFlowListQuery
	 * @param evalTask
	 * @return
	 */
	ElementFlowListContext getElementFlowListContext(EvalTaskFlowListQuery evalTaskFlowListQuery,
			EvalTask evalTask);

}

