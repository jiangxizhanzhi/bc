package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

public enum MessageType {

	/**
	 * 消息类型
	 */

	SINGLE_NODE_DYNAMIC(1, "single_node_dynamic"),
	MULTI_NODE_DYNAMIC(2, "multi_node_dynamic"),
	ATTACK_EVENT(3, "attack_event"),
	ATTACK_EVENT_EE(4, "attack_event_element-element"),
	ATTACK_EVENT_EA(5, "attack_event_element-area"),
	ATTACK_EFFECT(6, "attack_effect"),
	NODE_STATUS(7, "link_status"),
	ATTACK_SCHEDULE(8,"attack_schedule"),
	SCENE_CONFIG(9,"scene_config"),
	DIRECTOR_SCHEDULING_EE(10,"director_scheduling_element-element"),
	DIRECTOR_SCHEDULING_EA(11,"director_scheduling_element-area");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	MessageType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
