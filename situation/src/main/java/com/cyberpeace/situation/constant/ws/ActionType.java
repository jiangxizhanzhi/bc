package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

import java.util.Arrays;

public enum ActionType {

	/**
	 * 消息类型
	 * compound: 同时支持节点和区域
	 * element: 只支持节点
	 * area: 只支持区域
	 */

	NETWORK_SCANNING(0, "网络扫描", "compound",true,true),
	VIRUS_ATTACKS(1, "病毒攻击", "compound",true,true),
	TROJAN(2, "木马攻击", "element",true,true),
	DDOS(3, "DDOS", "compound",true,true),
	HOST_ATTACK(4, "主机攻占", "element",true,true),
	TRY_ATTACK(5, "尝试攻击", "element",true,true),
	SUCCESS_ATTACK(6, "攻击成功", "element",true,true),
	REPAIR(7, "修复", "element",true,true),
	START_ATTACK(8, "起始节点特效", "element",true,true),
	NORMAL(9, "普通攻击", "element",true,true),
	NONE(10, "无", "element",true,true);

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String type;

	//源节点是否可选
	@Getter
	private boolean source ;

	//持续时间是否可选
	@Getter
	private boolean durationTime;

	ActionType(Integer code, String desc, String type, boolean source, boolean durationTime) {
		this.code = code;
		this.desc = desc;
		this.type = type;
		this.source = source;
		this.durationTime = durationTime;
	}

	public static Integer getRandomValue(ActionType[] values) {
		return values[(int) (Math.random() * values.length)].code;
	}

	public static ActionType getRandom(ActionType[] values) {
		return values[(int) (Math.random() * values.length)];
	}

	public static <T extends ActionType> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(null);
	}
}
