package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;

public interface RbRoomService extends IService<RbRoom> {

	/**
	 * 根据ids批量获取
	 * @param roomIds
	 * @return
	 */
	List<RbRoom> getByIds(List<String> roomIds);

	/**
	 * 获取所有红蓝房间
	 * @return
	 */
	List<RbRoom> getAll();

	/**
	 * 获取当前红蓝房间数量
	 * @return
	 */
	Integer countAll();
}
