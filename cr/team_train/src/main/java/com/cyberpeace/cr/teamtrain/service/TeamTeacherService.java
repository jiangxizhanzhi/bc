package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;

public interface TeamTeacherService {

	/**
	 * 获取评分列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<StudentScoreListRsp>}
	 */
	List<StudentScoreListRsp> getScoreList(String roomId);
}
