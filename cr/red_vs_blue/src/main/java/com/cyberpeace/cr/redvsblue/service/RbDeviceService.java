package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;

/**
 * 红蓝对抗装置Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface RbDeviceService extends IService<RbDevice> {

	/**
	 * 获取训练的主机占用情况
	 *
	 * @param roomId 红蓝对抗实例id
	 * @param device 主机id
	 * @return 占用情况
	 */
	List<RbDeviceContext> getByRoomIdAndOptionalDevice(String roomId, String device);

	/**
	 * 获取主机
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param deviceId 主机id
	 * @return 主机
	 */
	RbDevice getByRoomIdAndDevice(String roomId, String deviceId);

	/**
	 * 创建红蓝对抗主机占用实例
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param deviceId 主机id
	 * @param userId   用户id
	 * @return 结果（布尔值）
	 */
	Boolean create(String roomId, String deviceId, String userId);

	/**
	 * 关闭场景、删除房间时，删除 rb device
	 *
	 * @param roomId 红蓝对抗实例id
	 */
	void removeByRoomId(String roomId);

	/**
	 * 删除某用户对某靶机的占用
	 *
	 * @param roomId   红蓝对抗实例id
	 * @param userId   用户id
	 * @param deviceId 主机id
	 * @return 结果（布尔值）
	 */
	Boolean removeByRoomIdAndUserIdAndDeviceId(String roomId, String userId, String deviceId);
}
