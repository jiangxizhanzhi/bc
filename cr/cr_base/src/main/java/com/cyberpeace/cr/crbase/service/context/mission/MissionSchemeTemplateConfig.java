package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

@Data
public class MissionSchemeTemplateConfig implements Serializable {

	private Integer template;

	private Integer color;

	public MissionSchemeTemplateConfig(Integer template, Integer color) {
		this.template = template;
		this.color = color;
	}

}
