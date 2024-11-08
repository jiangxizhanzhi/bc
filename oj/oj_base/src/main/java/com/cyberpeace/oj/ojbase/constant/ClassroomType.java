package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 教室类型
 *
 * @author SN
 * @date 2024/11/08
 */
public enum ClassroomType implements GenericEnum {

	/**
	 * 房间类型
	 */
	THEORY(1, "理论赛"),
	/**
	 * OM
	 */
	OM(2, "运维赛"),
	/**
	 * CTF
	 */
	CTF(3, "夺旗赛"),
	/**
	 * 竞赛
	 */
	AD(4, "攻防赛"),
	/**
	 * 渗透
	 */
	PERMEATE(5, "渗透赛"),
	/**
	 * 类
	 */
	CLASS(8, "课程表"),
	/**
	 * 团队
	 */
	TEAM(9, "团队训练"),
	/**
	 * RB
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
