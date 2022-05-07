package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum ClassroomType implements GenericEnum {

	/** 房间类型*/
	THEORY(1, "理论赛"),
	OM(2, "运维赛"),
	CTF(3, "夺旗赛"),
	AD(4, "攻防赛"),
	PERMEATE(5, "渗透赛"),
	CLASS(8, "课程表"),
	TEAM(9, "单兵训练"),
	RB(10, "红蓝对抗");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	ClassroomType(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
