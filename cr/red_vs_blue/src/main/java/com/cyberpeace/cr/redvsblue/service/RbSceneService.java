package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.redvsblue.entity.RbAttackPath;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneViewContext;
import com.cyberpeace.cs.service.context.scene.SceneData;

public interface RbSceneService {

	/**
	 * 开启场景
	 * @param roomId 红蓝对抗实例id
	 * @param userId 用户id
	 * @return 场景id
	 */
	String startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @param roomId 红蓝对抗实例id
	 * @param roleId 角色id
	 * @return 场景数据
	 */
	RbSceneViewContext viewScene(String roomId, String roleId);

	/**
	 * 关闭场景
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 */
	Boolean stopScene(String roomId);

	/**
	 * 关闭场景后清理本地数据
	 * @param room 红蓝对抗实例id
	 */
	void clearLocal(RbRoom room);

	/**
	 * 暂停场景
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 */
	Boolean pauseScene(String roomId);

	/**
	 * 恢复场景
	 * @param roomId 红蓝对抗实例id
	 * @return 结果（布尔值）
	 */
	Boolean unpauseScene(String roomId);

	/**
	 * 获取流量列表
	 * @param roomId 红蓝对抗实例id
	 * @return 流量列表
	 */
	List<Flow> getFlowList(String roomId);

	/**
	 * 获取资产列表
	 *
	 * @param roomId   房间id
	 * @param category 类别
	 * @param name     名称
	 * @return {@code JSONArray}
	 */
	JSONArray getAssetList(String roomId, Integer category, String name);

	/**
	 * 获取节点统计数据
	 *
	 * @param roomId 房间id
	 * @return {@code JSONObject}
	 */
	JSONObject getNodeStatistics(String roomId);

	SceneData getSceneData(RbRoom room);

	List<RbAttackPath> queryAttackPath(String sceneId);

	void addAttackPath(RbAttackPath attackPath);

	void deleteAttackPath(String resourceId, String sceneId);

	void updateRbAttackPathByTemplateId(String resourceId, List<RbAttackPath> attackPaths);

	void batchDeleteByTemplateIds(List<String> rbTemplateIds);

	List<RbAttackPath> queryAttackPathByTemplateId(String templateId);
}
