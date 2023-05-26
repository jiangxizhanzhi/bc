package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.context.TeamSceneViewContext;

public interface TeamSceneService {

	/**
	 * 开启场景
	 * @param roomId 团队训练实例id
	 * @param userId 用户id
	 * @return 场景id
	 */
	String startScene(String roomId, String userId);

	/**
	 * 查看场景
	 * @param roomId 团队训练实例id
	 * @param roleId 角色id
	 * @return 场景数据
	 */
	TeamSceneViewContext viewScene(String roomId, String roleId);

	/**
	 * 关闭场景
	 * @param roomId 团队训练实例id
	 * @return 结果（布尔值）
	 */
	Boolean stopScene(String roomId);

	/**
	 * 关闭场景后清除本地数据
	 * @param room 团队训练实例id
	 */
	void clearLocal(TeamRoom room);

	/**
	 * 暂停场景
	 * @param roomId
	 * @return
	 */
	Boolean pauseScene(String roomId);

	/**
	 * 恢复场景
	 * @param roomId
	 * @return
	 */
	Boolean unpauseScene(String roomId);

	/**
	 * 获取流量列表
	 * @param roomId 团队训练实例id
	 * @return 流量列表
	 */
	List<Flow> getFlowList(String roomId);
}
