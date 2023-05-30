package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cs.service.context.IdNameType;

public interface TeamTeacherService {

	/**
	 * 获取评分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 */
	List<StudentScoreListRsp> getScoreList(String roomId);

	/**
	 * 获取角色列表，用以评估列表角色下拉单
	 *
	 * @param roomId 房间id
	 * @return {@code List<IdNameType>}
	 */
	List<IdNameType> getRoleList(String roomId);

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData}
	 */
	TimeLineData getReplayTimeline(String roomId, String userId);
}
