package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;

public interface TeamCommonDeviceService extends IService<TeamCommonDevice> {

	/**
	 * 根据团队房间id和设备添加人 查询已添加的常用设别列表
	 * @param roomId 团队房间id
	 * @param collectUserId 设备添加人
	 * @return 常用设备列表
	 */
    List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

	/**
	 * 获取所有已添加为常用资产的 resourceIds
	 * @return 常用资产的 deviceIds
	 */
	List<String> getAllCollectedCommonDeviceDeviceIds();

	/**
	 * 批量添加常用资产
	 * @param commonDeviceContextList 常用资产列表
	 */
	void addListToCollect(List<TeamCommonDeviceContext> commonDeviceContextList);

	/**
	 * 添加常用设备
	 * @param commonDeviceContext 常用设备json
	 */
    void addToCollect(TeamCommonDeviceContext commonDeviceContext);

	/**
	 * 根据常用设备uuid删除
	 * @param resourceId 常用设备uuid
	 */
    void delCollect(String resourceId);

	/**
	 * 按节点id删除
	 *
	 * @param nodeId 节点id
	 */
	void deleteByDeviceId(String nodeId);
}
