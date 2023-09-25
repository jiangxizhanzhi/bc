package com.cyberpeace.situation.model.ws;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsDefenseInfo implements Serializable {

	private String targetId;

	private String desc;

	private String defenseName;

	private String tool;

	private String leak;

	private String result;

	private Integer duration;
}
