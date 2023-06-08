package com.cyberpeace.cr.crbase.constant;

/**
 * ws推送code
 * 不限于任务相关
 */
public interface MissionWsCode {

	Integer RVB_MISSION_CODE = 1;

	Integer TEAM_MISSION_CODE = 2;

	Integer MISSION_WS_UPDATE_STATUS = 3;

	Integer NOTICE_Code = 4;

	Integer EXERCISE_PAPER_CODE = 5;

	/**
	 * 场景更新
	 */
	Integer SCENE_UPDATED = 6;

	/**
	 * 任务下发
	 */
	Integer MISSION_RELEASE = 7;

	/**
	 * 新的任务提交
	 */
	Integer MISSION_SUBMISSION = 8;
}
