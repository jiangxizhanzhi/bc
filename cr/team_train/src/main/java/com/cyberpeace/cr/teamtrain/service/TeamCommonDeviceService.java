package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;

/**
 * 团队常见装置Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface TeamCommonDeviceService extends IService<TeamCommonDevice> {

	/**
	 * 过滤可视常用资产
	 *
	 * @param roomId           红蓝房间id
	 * @param allCommonDevices 全部常用资产
	 * @return 已过滤常用资产
	 * @throws
	 * @see String
	 * @see List<TeamCommonDeviceContext>
	 * @see List<TeamCommonDeviceContext>
	 */
	List<TeamCommonDeviceContext> filterVisibleCommonDevices(String roomId,
			List<TeamCommonDeviceContext> allCommonDevices);

	/**
	 * 根据团队房间id和设备添加人 查询已添加的常用设别列表
	 *
	 * @param roomId        团队房间id
	 * @param collectUserId 设备添加人
	 * @return 常用设备列表
	 * @throws
	 * @see String
	 * @see String
	 * @see List<TeamCommonDevice>
	 */
	List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

	/**
	 * 获取所有已添加为常用资产的 resourceIds
	 *
	 * @param roomId 房间id
	 * @return 常用资产的 deviceIds
	 * @throws
	 * @see String
	 * @see List<TeamCommonDevice>
	 */
	List<TeamCommonDevice> getAllCollectedCommonDeviceDevices(String roomId);

	/**
	 * 批量添加常用资产
	 *
	 * @param commonDeviceContextList 常用资产列表
	 * @throws
	 * @see List<TeamCommonDeviceContext>
	 */
	void addListToCollect(List<TeamCommonDeviceContext> commonDeviceContextList);

	/**
	 * 添加常用设备
	 *
	 * @param commonDeviceContext 常用设备json
	 * @throws
	 * @see TeamCommonDeviceContext
	 */
	void addToCollect(TeamCommonDeviceContext commonDeviceContext);

	/**
	 * 根据常用设备uuid删除
	 *
	 * @param resourceId 常用设备uuid
	 * @throws
	 * @see String
	 */
	void delCollect(String resourceId);

	/**
	 * 按节点id删除
	 *
	 * @param nodeId 节点id
	 * @throws
	 * @see String
	 */
	void deleteByDeviceId(String nodeId);

	/**
	 * 根据房间id删除 常用设备
	 *
	 * @param roomId 房间id
	 * @throws
	 * @see String
	 */
	void delByRoomId(String roomId);
}
