package com.cyberpeace.cr.crbase.constant;

/**
 * ws推送code
 * <p>
 * <p>
 * 不限于任务相关
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface MissionWsCode {

	/**
	 * RVB任务代号
	 *
	 * @see Integer
	 */
	Integer RVB_MISSION_CODE = 1;

	/**
	 * 团队任务代号
	 *
	 * @see Integer
	 */
	Integer TEAM_MISSION_CODE = 2;

	/**
	 * 任务WS更新状态
	 *
	 * @see Integer
	 */
	Integer MISSION_WS_UPDATE_STATUS = 3;

	/**
	 * 通知代号
	 *
	 * @see Integer
	 */
	Integer NOTICE_Code = 4;

	/**
	 * 锻炼试卷代号
	 *
	 * @see Integer
	 */
	Integer EXERCISE_PAPER_CODE = 5;

	/**
	 * 任务下发
	 *
	 * @see Integer
	 */
	Integer MISSION_RELEASE = 7;

	/**
	 * 新的任务提交
	 *
	 * @see Integer
	 */
	Integer MISSION_SUBMISSION = 8;
}
