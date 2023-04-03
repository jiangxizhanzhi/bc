package com.cyberpeace.situation.model.ws;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsMissionDynamic implements Serializable {

	private String category;

	private String description;

	private Integer duration;

	private Boolean lookAtTarget;

	private String roleType;

	private String sceneId;

	private String targetId;

	private Integer type;

	public WsMissionDynamic() {
		this.setCategory("attack");
		this.setDuration(7);
		this.setLookAtTarget(true);
	}

	public WsMissionDynamic(String description, String roleType, String sceneId, String targetId, Integer type) {
		this.description = description;
		this.roleType = roleType;
		this.sceneId = sceneId;
		this.targetId = targetId;
		this.type = type;
		this.setCategory("attack");
		this.setDuration(7);
		this.setLookAtTarget(true);
	}
}
