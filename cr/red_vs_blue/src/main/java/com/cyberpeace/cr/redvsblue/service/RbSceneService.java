package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneViewContext;

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
}
