package com.cyberpeace.common.constant;

/**
 * 中台场景状态不断
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface MpSceneStatusConstant {

	/**
	 * 暂停
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_PAUSE_EVENT_CODE = 6;

	/**
	 * 恢复
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_UNPAUSE_EVENT_CODE = 7;

	/**
	 * 场景删除事件代号
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_DELETE_EVENT_CODE = 9;

	/**
	 * 场景事件完成代号
	 *
	 * @see Integer
	 */
	Integer MP_SCENE_PROGRESS_FINISH_CODE = 4;
}
