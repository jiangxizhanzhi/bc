package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

public enum MessageType {

	/**
	 * 消息类型
	 */

	SingleNodeDynamic(1, "single_node_dynamic"),
	MultiNodeDynamic(2, "multi_node_dynamic"),
	AttackEvent(3, "attack_event"),
	AttackEventEE(4, "attack_event_element-element"),
	AttackEventEA(5, "attack_event_element-area"),
	AttackEffect(6, "attack_effect"),
	NodeStatus(7, "link_status"),
	AttackSchedule(8,"attack_schedule"),
	SceneConfig(9,"scene_config"),
	RbNodeStatus(10,"rb_node_status"),
	SituationTime(11, "situation_time"),
	DefenseDynamic(12,"defense_dynamic"),
	PlaybackSchedule(13, "playback_schedule"),
	AttackInfo(14, "attack_info"),
	DefenseInfo(15, "defense_info");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	MessageType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
