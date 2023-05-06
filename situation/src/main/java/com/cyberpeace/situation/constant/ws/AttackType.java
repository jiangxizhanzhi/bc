package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

/**
 * 攻击类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum AttackType {

	/**
	 * 网络扫描
	 */
	NETWORK_SCANNING(ActionType.NETWORK_SCANNING, "网络扫描", "Network Scanning", "network-scanning"),
	/**
	 * 病毒攻击
	 */
	VIRUS_ATTACKS(ActionType.VIRUS_ATTACKS, "病毒攻击", "Virus Attacks", "virus-attacks"),
	/**
	 * 木马攻击
	 */
	TROJAN(ActionType.TROJAN, "木马攻击", "Trojan", "trojan"),
	/**
	 * DDOS攻击
	 */
	DDOS(ActionType.DDOS, "DDOS攻击", "DDOS", "ddos"),
	/**
	 * 主机攻占
	 */
	HOST_ATTACK(ActionType.HOST_ATTACK, "主机攻占", "Host Attack", "host-attack"),
	/**
	 * 尝试攻击
	 */
	TRY_ATTACK(ActionType.TRY_ATTACK, "尝试攻击", "Try Attack", "try-attack"),
	/**
	 * 有效攻击
	 */
	SUCCESS_ATTACK(ActionType.SUCCESS_ATTACK, "有效攻击", "Success Attack", "success-attack"),
	/**
	 * 主机修复
	 */
	REPAIR(ActionType.REPAIR, "主机修复", "Repair", "repair"),
	/**
	 * 发起攻击
	 */
	START_ATTACK(ActionType.START_ATTACK, "发起攻击", "Start Attack", "start-attack"),
	/**
	 * 一般攻击
	 */
	NORMAL(ActionType.NORMAL, "一般攻击", "Normal", "normal"),
	/**
	 * 无攻击
	 */
	NONE(ActionType.NONE, "无攻击", "None", "none"),
	/**
	 * 多媒体动效
	 */
	MEDIA_ANIMATION(ActionType.NONE, "多媒体动效", "Media Animation", "media-animation"),
	/**
	 * 区域高亮
	 */
	HIGHLIGHT_AREA(ActionType.HIGHLIGHT_AREA, "区域高亮", "Highlight Area", "highlight-area"),
	/**
	 * 节点聚焦
	 */
	HIGHLIGHT_ELEMENT(ActionType.HIGHLIGHT_ELEMENT, "节点聚焦", "Highlight Element",
			"highlight-element"),
	/**
	 * 链路断线
	 */
	LINK_POWER_OFF(ActionType.LINK_POWER_OFF, "链路断线", "Link Poweroff", "link-poweroff");

	/**
	 * 动作类型
	 */
	@Getter
	private ActionType actionType;

	/**
	 * 中文描述
	 */
	@Getter
	private String cnDesc;

	/**
	 * 英文描述
	 */
	@Getter
	private String enDesc;

	/**
	 * 关键字
	 */
	@Getter
	private String keyWord;

	AttackType(ActionType actionType, String cnDesc, String enDesc, String keyWord) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	/**
	 * 根据英文描述获取枚举
	 *
	 * @param enDesc    英文描述
	 * @param enumClass 枚举类
	 * @return {@code T}
	 */
	public static <T extends AttackType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	/**
	 * 根据请求语言获取对应描述
	 *
	 * @return {@code String}
	 */
	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
