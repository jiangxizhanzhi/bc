package com.cyberpeace.cr.cebase.model.crevent;

import lombok.Data;

@Data
public class CrEventOutput {

	private String resourceId;

	private String name;

	private String description;

	private Integer category;

	private Integer difficulty;

	private Integer ceType;

	public CrEventOutput(CrEvent crEvent) {
		resourceId = crEvent.getResourceId();
		name = crEvent.getName();
		description = crEvent.getDescription();
		category = crEvent.getCategory();
		difficulty = crEvent.getDifficulty();
		ceType = crEvent.getCeType();
	}
}
