package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;

public interface RbCommonDeviceService extends IService<RbCommonDevice> {

    List<RbCommonDevice> getCollectedCommonDevices(String roomId, String collectUserId);

    void addToCollect(RbCommonDeviceContext commonDeviceContext);

    void delCollect(String resourceId);

	/**
	 * 按设备id删除
	 *
	 * @param nodeId 节点id
	 */
	void deleteByDeviceId(String nodeId);
}
