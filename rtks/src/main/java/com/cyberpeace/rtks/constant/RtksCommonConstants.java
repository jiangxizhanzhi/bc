package com.cyberpeace.rtks.constant;

/**
 * RTKS常用常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface RtksCommonConstants {

	/**
	 * 合格的匹配度
	 *
	 * @see Integer
	 */
	Integer QUALIFIED_MATCH = 60;

	/**
	 * 优秀的匹配度
	 *
	 * @see Integer
	 */
	Integer EXCELLENT_MATCH = 80;

	/**
	 * 岗位之星的匹配度
	 *
	 * @see Integer
	 */
	Integer OUTSTANDING_MATCH = 90;

	/**
	 * rtks_level 最上层id
	 *
	 * @see String
	 */
	String FRAME_MASTER_LEVEL_ID = "default";
}
