package com.cyberpeace.cr.redvsblue.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;

public interface RbTimeManagementService extends IService<RbTimeManagement> {

	/**
	 * 根据红蓝房间id获取时间管理
	 * @param roomId 红蓝房间id
	 * @return 时间管理
	 */
	RbTimeManagement getByRoomId(String roomId);

	/**
	 * 获取所有已开始的红蓝房间id
	 * @return
	 */
	List<String> getStartedRoomIds();

	/**
	 * 获取所有时间管理
	 * @return
	 */
	List<RbTimeManagement> getAll();

	/**
	 * 获取所有已停止的
	 * @return
	 */
	List<RbTimeManagement> getStopped();

	/**
	 * 获取所有未停止的
	 * @return
	 */
	List<RbTimeManagement> getUnstopped();

	/**
	 * 更新时间管理器
	 * @param timeManagement 时间管理实例
	 * @param room 红蓝对抗实例
	 * @param status 更新状态
	 * @param userId 用户id
	 */
	void updateTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId);
	
	/**
	 * 创建时间管理器
	 * @param timeManagement 时间管理
	 * @param room 红蓝实例
	 * @param status 初始状态
	 * @param userId 用户id
	 */
	void addTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status, String userId);

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String}
	 */
	String getTimeCost(String roomId);

	String getColonTimeCost(String roomId);

	/**
	 * 获取实际的训练结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date}
	 */
	Date getActualEndTime(String roomId);
}
