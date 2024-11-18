package com.cyberpeace.cr.redvsblue.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;

/**
 * 红蓝对抗时间管理Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface RbTimeManagementService extends IService<RbTimeManagement> {

	/**
	 * 根据红蓝房间id获取时间管理
	 *
	 * @param roomId 红蓝房间id
	 * @return 时间管理
	 * @throws
	 * @see String
	 * @see RbTimeManagement
	 */
	RbTimeManagement getByRoomId(String roomId);

	/**
	 * 获取所有已开始的红蓝房间id
	 *
	 * @return
	 * @throws
	 * @see List<String>
	 */
	List<String> getStartedRoomIds();

	/**
	 * 获取所有时间管理
	 *
	 * @return
	 * @throws
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> getAll();

	/**
	 * 获取所有已停止的
	 *
	 * @return
	 * @throws
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> getStopped();

	/**
	 * 获取所有未停止的
	 *
	 * @return
	 * @throws
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> getUnstopped();

	/**
	 * 更新时间管理器
	 *
	 * @param timeManagement 时间管理实例
	 * @param room           红蓝对抗实例
	 * @param status         更新状态
	 * @param userId         用户id
	 * @throws
	 * @see RbTimeManagement
	 * @see RbRoom
	 * @see Integer
	 * @see String
	 */
	void updateTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status,
			String userId);

	/**
	 * 创建时间管理器
	 *
	 * @param timeManagement 时间管理
	 * @param room           红蓝实例
	 * @param status         初始状态
	 * @param userId         用户id
	 * @throws
	 * @see RbTimeManagement
	 * @see RbRoom
	 * @see Integer
	 * @see String
	 */
	void addTimeManagement(RbTimeManagement timeManagement, RbRoom room, Integer status, String userId);

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String}
	 * @throws
	 * @see String
	 * @see String
	 */
	String getTimeCost(String roomId);

	/**
	 * 获取结肠时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	String getColonTimeCost(String roomId);

	/**
	 * 获取实际的训练结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date}
	 * @throws
	 * @see String
	 * @see Date
	 */
	Date getActualEndTime(String roomId);
}
