package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;

public interface TeamTimeManagementService extends IService<TeamTimeManagement> {

	/**
	 * 根据房间id获取时间管理
	 * @param roomId 红蓝房间id
	 * @return 时间管理
	 */
	TeamTimeManagement getByRoomId(String roomId);

}
