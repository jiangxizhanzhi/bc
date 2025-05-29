package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;

import java.util.List;

/**
 * OJ当地场景Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `OjLocalSceneService` 的接口，该接口继承自 `IService<OjLocalScene>`。这个接口包含了多个方法，用于处理与 OJ 当地场景（OjLocalScene）相关的操作。以下是对这些方法的简要分析： 
 * ### 方法概述 
 * 1. **selectOjLocalScene** 
 * - **描述**：根据业务ID、共享状态和权限上下文查询 OJ 当地场景。 
 * - **参数**： 
 * - `businessId`：资源编号，如课程编号、课时编号或习题编号。 
 * - `shared`：是否共享，1 表示私有，2 表示共享。 
 * - `authContext`：权限数据。 
 * - **返回值**：`OjLocalScene` 对象。 
 * 2. **initOjLocalScene** 
 * - **描述**：初始化 OJ 当地场景。 
 * - **参数**：`sceneContext`：初始化场景的上下文信息。 
 * - **返回值**：初始化后的 `OjLocalScene` 对象。 
 * 3. **setDynamicFlag** 
 * - **描述**：在拓扑结构中注入动态标志命令。 
 * - **参数**： 
 * - `ojLocalScene`：当前的 OJ 当地场景对象。 
 * - `topoId`：拓扑 ID。 
 * - **返回值**：包含注入结果的 `JSONObject`。 
 * 4. **deleteLocalScene** 
 * - **描述**：删除指定的 OJ 当地场景。 
 * - **参数**：`ojLocalSceneId`：OJ 当地场景的 ID。 
 * - **返回值**：被删除的 `OjLocalScene` 对象。 
 * 5. **deleteLocalAndRemoteScene** 
 * - **描述**：同时删除本地和远程的 OJ 当地场景。 
 * - **参数**：`ojLocalScene`：当前的 OJ 当地场景对象。 
 * - **返回值**：删除操作的结果，布尔类型。 
 * 6. **getStartSceneMessage** 
 * - **描述**：组装场景启动完成的消息体。 
 * - **参数**： 
 * - `message`：场景启动相关消息。 
 * - `ojLocalScene`：当前的 OJ 当地场景对象。 
 * - **返回值**：包含消息内容的 `JSONObject`。 
 * 7. **checkSceneStartCount** 
 * - **描述**：检查场景启动数量是否超过最大数量。 
 * - **参数**： 
 * - `userId`：用户 ID。 
 * - `createCount`：创建计数。 
 * - **异常**：无。 
 * 8. **getSceneData** 
 * - **描述**：获取场景信息。 
 * - **参数**：`ojLocalScene`：当前的 OJ 当地场景对象。 
 * - **返回值**：包含场景数据的 `JSONObject`。 
 * 9. **selectMonitorList** 
 * - **描述**：获取监控信息列表。 
 * - **参数**：`ojLocalScenes`：OJ 当地场景的列表。 
 * - **返回值**：监控信息列表 `List<SceneMonitorBo>`。 
 * 10. **switchScreenRecord** 
 * - **描述**：切换录屏功能。 
 * - **参数**： 
 * - `topoId`：拓扑 ID。 
 * - `record`：是否开启录屏。 
 * - **返回值**：包含录屏状态的 `JSONObject`。 
 * ### 总结 
 * 这个接口主要处理 OJ 当地场景的查询、初始化、删除、启动消息组装、监控信息获取等操作。每个方法都有明确的职责，并且参数和返回值都经过精心设计，以确保清晰和可维护性。 
**/
public interface OjLocalSceneService extends IService<OjLocalScene> {

	/**
	 * 查询ojLocalScene数据 不包含
	 *
	 * @param businessId  资源编号 课程编号 课时编号 习题编号
	 * @param shared      是否共享 1=私有 2=共享
	 * @param authContext 前后台权限数据
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see OjLocalScene
	 */
	OjLocalScene selectOjLocalScene(String businessId, Integer shared, AuthContext authContext);

	/**
	 * 初始化ojLocalScene
	 *
	 * @param sceneContext
	 * @return
	 * @throws
	 * @see InitOjSceneContext
	 * @see OjLocalScene
	 */
	OjLocalScene initOjLocalScene(InitOjSceneContext sceneContext);

	/**
	 * 设置topo结构中注入flag命令
	 *
	 * @param ojLocalScene
	 * @param topoId       拓扑id
	 * @return
	 * @throws
	 * @see OjLocalScene
	 * @see JSONObject
	 * @see JSONObject
	 */
	JSONObject setDynamicFlag(OjLocalScene ojLocalScene, JSONObject topoId);

	/**
	 * 删除本地场景
	 *
	 * @param ojLocalSceneId OJ当地场景id
	 * @return {@code OjLocalScene }
	 * @throws
	 * @see String
	 * @see OjLocalScene
	 */
	OjLocalScene deleteLocalScene(String ojLocalSceneId);

	/**
	 * 删除本地和中台场景
	 *
	 * @param ojLocalScene
	 * @return
	 * @throws
	 * @see OjLocalScene
	 * @see Boolean
	 */
	Boolean deleteLocalAndRemoteScene(OjLocalScene ojLocalScene);

	/**
	 * 组装实训场景启动完成消息体
	 *
	 * @param message      场景启动相关消息
	 * @param ojLocalScene OJ当地场景
	 * @return
	 * @throws
	 * @see String
	 * @see OjLocalScene
	 * @see JSONObject
	 */
	JSONObject getStartSceneMessage(String message, OjLocalScene ojLocalScene);

	/**
	 * 检查场景启动数量是否超过最大数量
	 *
	 * @param userId
	 * @param createCount 创造计数
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void checkSceneStartCount(String userId, Integer createCount);

	/**
	 * 查询场景信息
	 *
	 * @param ojLocalScene
	 * @return
	 * @throws
	 * @see OjLocalScene
	 * @see JSONObject
	 */
	JSONObject getSceneData(OjLocalScene ojLocalScene);

	/**
	 * 获取监控信息列表
	 *
	 * @param ojLocalScenes
	 * @return
	 * @throws
	 * @see List<OjLocalScene>
	 * @see List<SceneMonitorBo>
	 */
	List<SceneMonitorBo> selectMonitorList(List<OjLocalScene> ojLocalScenes);

	/**
	 * 是否开启录屏
	 *
	 * @param topoId
	 * @param record
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see JSONObject
	 */
	JSONObject switchScreenRecord(String topoId, Boolean record);

}

