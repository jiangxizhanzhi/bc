package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;

public interface TeamCommonDeviceService extends IService<TeamCommonDevice> {

    List<TeamCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

    void addToCollect(TeamCommonDeviceContext commonDeviceContext);

    void delCollect(String resourceId);

	/**
	 * 按节点id删除
	 *
	 * @param nodeId 节点id
	 */
	void deleteByDeviceId(String nodeId);
}
