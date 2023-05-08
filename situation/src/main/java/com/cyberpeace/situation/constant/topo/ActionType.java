package com.cyberpeace.situation.constant.topo;

import java.util.Arrays;

import lombok.Getter;

/**
 * 拓扑节点动作类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum ActionType {

	/**
	 * 网络扫描
	 */
	NETWORK_SCANNING(0, "网络扫描", "compound", true, true),

	/**
	 * 病毒攻击
	 */
	VIRUS_ATTACKS(1, "病毒攻击", "compound", true, true),

	/**
	 * 木马攻击
	 */
	TROJAN(2, "木马攻击", "element", true, true),

	/**
	 * DDOS攻击
	 */
	DDOS(3, "DDOS攻击", "compound", true, true),

	/**
	 * 主机攻占
	 */
	HOST_ATTACK(4, "主机攻占", "element", true, true),

	/**
	 * 尝试攻击
	 */
	TRY_ATTACK(5, "尝试攻击", "element", true, true),

	/**
	 * 有效攻击
	 */
	SUCCESS_ATTACK(6, "有效攻击", "element", true, true),

	/**
	 * 主机修复
	 */
	REPAIR(7, "主机修复", "element", true, true),

	/**
	 * 发起攻击
	 */
	START_ATTACK(8, "发起攻击", "element", true, true),

	/**
	 * 一般攻击
	 */
	NORMAL(9, "一般攻击", "element", true, true),

	/**
	 * 无攻击
	 */
	NONE(10, "无攻击", "element", true, true),

	/**
	 * 区域高亮
	 */
	HIGHLIGHT_AREA(11, "区域高亮", "area", false, true),

	/**
	 * 节点聚焦
	 */
	HIGHLIGHT_ELEMENT(12, "节点聚焦", "element", false, true),

	/**
	 * 链路断线
	 */
	LINK_POWER_OFF(13, "链路断线", "element", true, true);

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	/**
	 * 类型
	 */
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

	/**
	 * 获取随机动作类型代码
	 *
	 * @param values 值
	 * @return {@code Integer}
	 */
	public static Integer getRandomValue(ActionType[] values) {
		return values[(int) (Math.random() * values.length)].code;
	}

	public static ActionType getRandom(ActionType[] values) {
		return values[(int) (Math.random() * values.length)];
	}

	/**
	 * 根据代码获取枚举
	 *
	 * @param code      代码
	 * @param enumClass 枚举类
	 * @return {@code T}
	 */
	public static <T extends ActionType> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(null);
	}
}
