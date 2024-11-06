package com.cyberpeace.situation.constant.ws;

import java.util.Arrays;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

public enum AttackType {

	/**
	 * 攻击类型
	 */

    NETWORK_SCANNING(ActionType.NETWORK_SCANNING, "网络扫描", "Network Scanning","network-scanning"),
    VIRUS_ATTACKS(ActionType.VIRUS_ATTACKS, "病毒攻击", "Virus Attacks","virus-attacks"),
    TROJAN(ActionType.TROJAN, "木马攻击", "Trojan","trojan"),
    DDOS(ActionType.DDOS, "DDOS攻击", "DDOS","ddos"),
    HOST_ATTACK(ActionType.HOST_ATTACK, "主机攻占", "Host Attack","host-attack"),
    TRY_ATTACK(ActionType.TRY_ATTACK, "尝试攻击", "Try Attack","try-attack"),
    SUCCESS_ATTACK(ActionType.SUCCESS_ATTACK, "有效攻击", "Success Attack","success-attack"),
    REPAIR(ActionType.REPAIR, "主机修复", "Repair","repair"),
    START_ATTACK(ActionType.START_ATTACK, "发起攻击", "Start Attack","start-attack"),
    NORMAL(ActionType.NORMAL, "一般攻击", "Normal","normal"),
    NONE(ActionType.NONE, "无攻击", "None","none"),
    MEDIA_ANIMATION(ActionType.NONE,"多媒体动效","Media Animation","media-animation"),
    HIGHLIGHT_AREA(ActionType.HIGHLIGHT_AREA,"区域高亮","Highlight Area","highlight-area"),
    HIGHLIGHT_ELEMENT(ActionType.HIGHLIGHT_ELEMENT,"节点聚焦","Highlight Element","highlight-element"),
    LINK_POWER_OFF(ActionType.LINK_POWER_OFF,"链路断线","Link Poweroff","link-poweroff"),
	NODE_DARKENS(ActionType.NODE_DARKENS,"节点变暗","Node darkens","node-darkens"),
	NODE_DARKENS_BAK(ActionType.NODE_DARKENS_BAK,"节点恢复","Node darkens bak","node-darkens-bak");

    @Getter
	private ActionType actionType;

	@Getter
	private String cnDesc;

	@Getter
	private String enDesc;

	@Getter
	private String keyWord;

	AttackType(ActionType actionType, String cnDesc, String enDesc, String keyWord) {
		this.actionType = actionType;
		this.cnDesc = cnDesc;
		this.enDesc = enDesc;
		this.keyWord = keyWord;
	}

	/**
	 * 根据enDesc获取枚举
	 */
	public static <T extends AttackType> T getEnumByEnDesc(String enDesc, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getEnDesc().equals(enDesc))
				.findFirst().orElse(null);
	}

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}

}
