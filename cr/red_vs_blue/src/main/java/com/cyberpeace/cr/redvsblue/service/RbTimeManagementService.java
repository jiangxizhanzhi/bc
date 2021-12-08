package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;

public interface RbTimeManagementService extends IService<RbTimeManagement> {

	/**
	 * 根据红蓝房间id获取
	 * @param roomId 
	 * @return
	 */
	RbTimeManagement getByRoomId(String roomId);

	/**
	 * 获取所有已开始的红蓝房间id
	 * @return
	 */
	List<String> getStartedRoomIds();

	/**
	 * 获取所有
	 * @return
	 */
	List<RbTimeManagement> getALl();

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
}
