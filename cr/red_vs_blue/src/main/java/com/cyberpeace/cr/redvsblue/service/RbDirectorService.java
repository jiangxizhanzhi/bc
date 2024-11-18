package com.cyberpeace.cr.redvsblue.service;

import com.cyberpeace.cr.crbase.service.context.mission.RedBlueTaskReleaseProgress;

/**
 * 红蓝对抗导演Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface RbDirectorService {

	/**
	 * 获取任务下发进步
	 *
	 * @param roomId 房间id
	 * @return {@code RedBlueTaskReleaseProgress}
	 * @throws
	 * @see String
	 * @see RedBlueTaskReleaseProgress
	 */
	RedBlueTaskReleaseProgress getTaskReleaseProgress(String roomId);

	/**
	 * 查看复盘条件是否满足
	 * 满足则返回scene_id
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean}
	 * @throws
	 * @see String
	 * @see String
	 */
	String getReplaySceneId(String roomId);
}
