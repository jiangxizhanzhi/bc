package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;

/**
 *
 */
public interface RbDeviceService extends IService<RbDevice> {

	/**
	 * 获取训练的主机占用情况
	 * @param roomId
	 * @param device
	 * @return
	 */
	List<RbDeviceContext> getByRoomIdAndOptionalDevice(String roomId, String device);

	RbDevice getByRoomIdAndDevice(String roomId, String deviceId);

	/**
	 * 创建 rb device
	 */
	Boolean create(String roomId, String deviceId, String userId);

	/**
	 * 关闭场景、删除房间时，删除 rb device
	 */
	void removeByRoomId(String roomId);

	/**
	 * 删除某用户对某靶机的占用
	 */
	Boolean removeByRoomIdAndUserIdAndDeviceId(String roomId, String userId, String deviceId);
}
