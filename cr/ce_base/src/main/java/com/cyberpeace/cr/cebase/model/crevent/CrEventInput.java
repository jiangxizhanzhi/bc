package com.cyberpeace.cr.cebase.model.crevent;

import lombok.Data;

@Data
public class CrEventInput {

	private String name;

	private String description;

	private Integer category;

	private Integer difficulty;

	public CrEvent toEntity() {
		CrEvent crEvent = new CrEvent();
		crEvent.setName(name);
		crEvent.setDescription(description);
		crEvent.setCategory(category);
		crEvent.setDifficulty(difficulty);
		return crEvent;
	}
}
