package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 房间类型
 *
 * @author SN
 * @date 2024/11/14
 */
public enum ClassroomType implements GenericEnum {

	/**
	 * 理论赛
	 */
	THEORY(1, "理论赛"),
	/**
	 * 运维赛
	 */
	OM(2, "运维赛"),
	/**
	 * 夺旗赛
	 */
	CTF(3, "夺旗赛"),
	/**
	 * 攻防赛
	 */
	AD(4, "攻防赛"),
	/**
	 * 渗透赛
	 */
	PERMEATE(5, "渗透赛"),
	/**
	 * 课程表
	 */
	CLASS(8, "课程表"),
	/**
	 * 团队训练
	 */
	TEAM(9, "团队训练"),
	/**
	 * 红蓝对抗
	 */
	RB(10, "红蓝对抗");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	ClassroomType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
