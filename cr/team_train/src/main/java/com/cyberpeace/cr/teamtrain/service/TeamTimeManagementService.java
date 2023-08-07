package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;

import java.util.List;

public interface TeamTimeManagementService extends IService<TeamTimeManagement> {

	/**
	 * 根据房间id获取时间管理
	 * @param roomId 红蓝房间id
	 * @return 时间管理
	 */
	TeamTimeManagement getByRoomId(String roomId);

	/**
	 * 获取所有已开始的红蓝房间id
	 * @return
	 */
	List<String> getStartedRoomIds();

	/**
	 * 获取所有时间管理
	 * @return
	 */
	List<TeamTimeManagement> getAll();

	/**
	 * 获取所有已停止的
	 * @return
	 */
	List<TeamTimeManagement> getStopped();

	/**
	 * 获取所有未停止的
	 * @return
	 */
	List<TeamTimeManagement> getUnstopped();

	/**
	 * 更新时间管理器
	 * @param timeManagement 时间管理实例
	 * @param room 红蓝对抗实例
	 * @param status 更新状态
	 * @param userId 用户id
	 */
	void updateTimeManagement(TeamTimeManagement timeManagement, TeamRoom room, Integer status,
							  String userId);
	
	/**
	 * 创建时间管理器
	 * @param timeManagement 时间管理
	 * @param room 红蓝实例
	 * @param status 初始状态
	 * @param userId 用户id
	 */
	void addTimeManagement(TeamTimeManagement timeManagement, TeamRoom room, Integer status, String userId);
}
