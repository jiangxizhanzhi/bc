package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import lombok.Getter;

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
	DDOS(3, "DDOS攻击", "compound",true,true),
	HOST_ATTACK(4, "主机攻占", "element",true,true),
	TRY_ATTACK(5, "尝试攻击", "element",true,true),
	SUCCESS_ATTACK(6, "有效攻击", "element",true,true),
	REPAIR(7, "主机修复", "element",true,true),
	START_ATTACK(8, "发起攻击", "element",true,true),
	NORMAL(9, "一般攻击", "element",true,true),
	NONE(10, "无攻击", "element",true,true),
	HIGHLIGHT_AREA(11,"区域高亮","area",false,true),
	HIGHLIGHT_ELEMENT(12,"节点聚焦","element",false,true),
	LINK_POWER_OFF(13,"链路断线","element",true,true),
	NODE_CONNECTED(14,"节点上线","element",false,false),
	NODE_DISCONNECTED(15,"节点下线","element",false,false);

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String type;

	/**
	 * 源节点是否可选
	 */
	@Getter
	private boolean source;

	/**
	 * 持续时间是否可选
	 */
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
