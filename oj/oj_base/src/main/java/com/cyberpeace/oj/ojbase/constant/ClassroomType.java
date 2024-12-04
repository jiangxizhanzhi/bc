package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 教室类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum ClassroomType implements GenericEnum {

	/**
	 * 房间类型
	 *
	 * @see ClassroomType
	 */
	THEORY(1, "理论赛"),
	/**
	 * OM
	 *
	 * @see ClassroomType
	 */
	OM(2, "运维赛"),
	/**
	 * CTF
	 *
	 * @see ClassroomType
	 */
	CTF(3, "夺旗赛"),
	/**
	 * 竞赛
	 *
	 * @see ClassroomType
	 */
	AD(4, "攻防赛"),
	/**
	 * 渗透
	 *
	 * @see ClassroomType
	 */
	PERMEATE(5, "渗透赛"),
	/**
	 * 类
	 *
	 * @see ClassroomType
	 */
	CLASS(8, "课程表"),
	/**
	 * 团队
	 *
	 * @see ClassroomType
	 */
	TEAM(9, "团队训练"),
	/**
	 * RB
	 *
	 * @see ClassroomType
	 */
	RB(10, "红蓝对抗");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	ClassroomType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
