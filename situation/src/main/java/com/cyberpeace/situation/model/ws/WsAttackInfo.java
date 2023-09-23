package com.cyberpeace.situation.model.ws;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsAttackInfo implements Serializable {

	private String sourceId;

	private String desc;

	private String sourceName;

	private String targetName;

	private String attackName;

	private String tool;

	private String leak;

	private String targetId;

	private String result;

	private Integer durationTime;
}
