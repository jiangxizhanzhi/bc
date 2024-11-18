package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneViewContext;
import com.cyberpeace.cs.service.context.scene.SceneData;

/**
 * 红蓝对抗场景Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface RbSceneService {

	/**
	 * 开启场景
	 *
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 * @return 场景id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	String startScene(String roomId, String userId);

	/**
	 * 查看场景
	 *
	 * @param roomId 红蓝对抗实例id
	 * @param roleId 角色id
	 * @return 场景数据
	 * @throws
	 * @see String
	 * @see String
	 * @see RbSceneViewContext
	 */
	RbSceneViewContext viewScene(String roomId, String roleId);

	/**
	 * 关闭场景
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean stopScene(String roomId);

	/**
	 * 关闭场景后清理本地数据
	 *
	 * @param room 红蓝对抗实例id
	 * @throws
	 * @see RbRoom
	 */
	void clearLocal(RbRoom room);

	/**
	 * 暂停场景
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean pauseScene(String roomId);

	/**
	 * 恢复场景
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean unpauseScene(String roomId);

	/**
	 * 获取流量列表
	 *
	 * @param roomId 红蓝对抗实例id
	 * @return 流量列表
	 * @throws
	 * @see String
	 * @see List<Flow>
	 */
	List<Flow> getFlowList(String roomId);

	/**
	 * 获取资产列表
	 *
	 * @param roomId   房间id
	 * @param category 类别
	 * @param name     名称
	 * @return {@code JSONArray}
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see JSONArray
	 */
	JSONArray getAssetList(String roomId, Integer category, String name);

	/**
	 * 获取节点统计数据
	 *
	 * @param roomId 房间id
	 * @return {@code JSONObject}
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getNodeStatistics(String roomId);

	/**
	 * 获取场景数据
	 *
	 * @param room 房间
	 * @return {@code SceneData }
	 * @throws
	 * @see RbRoom
	 * @see SceneData
	 */
	SceneData getSceneData(RbRoom room);
}
