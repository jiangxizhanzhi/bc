package com.cyberpeace.situation.constant.dashboard;

import lombok.Getter;

/**
 * 定制类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum CustomCode {

	/**
	 * 非定制大屏
	 */
	ZB(1, "非定制大屏"),

	/**
	 * 联管中心综合态势
	 */
	ZH_LG(2, "联管中心综合态势"),

	/**
	 * 分靶场综合态势
	 */
	ZH_RG(3, "分靶场综合态势"),

	/**
	 * 红蓝对抗-白方
	 */
	RB_W(4, "红蓝对抗-白方"),

	/**
	 * 红蓝对抗-红方
	 */
	RB_R(5, "红蓝对抗-红方"),

	/**
	 * 红蓝对抗-蓝方
	 */
	RB_B(6, "红蓝对抗-蓝方"),

	/**
	 * 作战试验-攻击
	 */
	ZZSY_GJ(7, "作战试验-攻击"),

	/**
	 * 作战试验-防御
	 */
	ZZSY_FY(8, "作战试验-防御"),

	/**
	 * 专项比武
	 */
	ZXBW(9, "专项比武"),

	/**
	 * 夺旗竞赛
	 */
	DQJS(10, "夺旗竞赛"),

	/**
	 * 夺旗竞赛-题目
	 */
	DQJS_TM(11, "夺旗竞赛-题目"),

	/**
	 * 自主训练
	 */
	ZZXL(12, "自主训练");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	CustomCode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
