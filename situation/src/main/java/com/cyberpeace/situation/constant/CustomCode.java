package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum CustomCode {

	/**
	 * 定制类型
	 */

	ZB(1, "非定制大屏"),
	ZH_LG(2, "联管中心综合态势"),
	ZH_RG(3, "分靶场综合态势"),
	RB_W(4, "红蓝对抗-白方"),
	RB_R(5, "红蓝对抗-红方"),
	RB_B(6, "红蓝对抗-蓝方"),
	ZZSY_GJ(7, "作战试验-攻击"),
	ZZSY_FY(8, "作战试验-防御"),
	ZXBW(9, "专项比武"),
	DQJS(10, "夺旗竞赛"),
	DQJS_TM(11, "夺旗竞赛-题目"),
	ZZXL(12, "自主训练");

	@Getter
	private Integer code;

	@Getter
	private String name;

	CustomCode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
