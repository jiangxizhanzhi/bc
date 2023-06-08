package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.crbase.service.context.mission.RedBlueTaskReleaseProgress;

public interface RbDirectorService {

	/**
	 * 获取任务下发进步
	 *
	 * @param roomId 房间id
	 * @return {@code RedBlueTaskReleaseProgress}
	 */
	RedBlueTaskReleaseProgress getTaskReleaseProgress(String roomId);

	/**
	 * 查看复盘条件是否满足
	 * 满足则返回scene_id
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean}
	 */
	String ifReplayEnabled(String roomId);
}
