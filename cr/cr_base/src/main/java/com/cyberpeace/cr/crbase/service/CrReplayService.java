package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;

public interface CrReplayService {

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 */
	List<TimeLineEvent> getReplayEvents(String roomId, String userId);
}
