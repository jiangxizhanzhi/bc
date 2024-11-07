package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;

/**
 * 靶场时间轴Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface CrTimelineService {

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 */
	List<TimeLineEvent> getReplayEvents(String roomId, String userId);

	/**
	 * 通过房间id获取时间轴事件
	 * <p>
	 * <p>
	 * 返回训练房间里所有学员的任务提交事件和标记事件
	 *
	 * @param roomId 房间id
	 * @return {@code List<TimeLineEvent>}
	 */
	List<TimeLineEvent> getTimelineEventsByRoomId(String roomId);
}
