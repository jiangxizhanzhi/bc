package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.room.DashboardInfo;

public interface RbRoomService extends IService<RbRoom> {

	Integer getTrainingStatus(String roomId);

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

	/** 列表查询团队训练
	 * @param listQuery
	 * @return
	 */
	IPage<RbRoomData> list(ListQuery listQuery);

	/**
	 * 获取当前红蓝房间数量
	 * @return
	 */
	Integer countAll();

	RbRoom create(RbRoomContext rbRoomContext);

	RbRoomData getRbRoomDataByRoomId(String resourceId);

	DashboardInfo getDashboardInfo(String resourceId);

	void updateRbRoom(RbRoomContext rbRoomContext, RbRoom rbRoom);

	void checkDirectorPermission(String roomId, String userId);

	void checkRoomOpening(String roomId);
}
