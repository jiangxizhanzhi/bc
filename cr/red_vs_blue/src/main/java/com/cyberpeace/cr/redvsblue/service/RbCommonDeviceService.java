package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

/**
 * RB常见装置Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface RbCommonDeviceService extends IService<RbCommonDevice> {

	/**
	 * 过滤可视常用资产
	 *
	 * @param roomId                  红蓝房间id
	 * @param commonDeviceContextList 可占用资产
	 * @return 已过滤常用资产
	 * @throws
	 * @see String
	 * @see List<RbCommonDeviceContext>
	 * @see List<RbCommonDeviceContext>
	 */
	List<RbCommonDeviceContext> filterVisibleCommonDevices(String roomId,
			List<RbCommonDeviceContext> commonDeviceContextList);

	/**
	 * 根据红蓝房间id和设备添加人 查询常用设备列表
	 *
	 * @param roomId        红蓝房间id
	 * @param collectUserId 常用设备添加人
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<RbCommonDevice>
	 */
	List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

	/**
	 * 获取全部常用设备resourceIds
	 *
	 * @param roomId 房间id
	 * @return 常用设备ids
	 * @throws
	 * @see String
	 * @see List<RbCommonDevice>
	 */
	List<RbCommonDevice> getAllCollectedCommonDeviceDevices(String roomId);

	/**
	 * 批量添加常用资产
	 *
	 * @param commonDeviceContextList 常用资产列表
	 * @throws
	 * @see List<RbCommonDeviceContext>
	 */
	void addListToCollect(List<RbCommonDeviceContext> commonDeviceContextList);

	/**
	 * 添加常用设备
	 *
	 * @param commonDeviceContext 常用设备
	 * @throws
	 * @see RbCommonDeviceContext
	 */
	void addToCollect(RbCommonDeviceContext commonDeviceContext);

	/**
	 * 删除常用设备
	 *
	 * @param resourceId 设备uuid
	 * @throws
	 * @see String
	 */
	void delCollect(String resourceId);

	/**
	 * 按设备id删除
	 *
	 * @param nodeId 节点id
	 * @throws
	 * @see String
	 */
	void deleteByDeviceId(String nodeId);

	/**
	 * 按照房间id删除常用资产
	 *
	 * @param roomId 房间id
	 * @throws
	 * @see String
	 */
	void delByRoomId(String roomId);
}
