package com.cyberpeace.cr.cebase.model.crevent;

import lombok.Data;
import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;

@Data
public class CrEventInput {

	private String name;

	private String description;

	private Integer category;

	private Integer difficulty;

	public CrEvent toEntity() {
		CrEvent crEvent = new CrEvent();
		crEvent.setName(getName());
		crEvent.setDescription(getDescription());
		crEvent.setCategory(getCategory());
		crEvent.setDifficulty(getDifficulty());
		return crEvent;
	}
}
