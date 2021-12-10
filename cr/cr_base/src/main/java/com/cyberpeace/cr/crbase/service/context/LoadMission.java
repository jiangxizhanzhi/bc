package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;

@Data
public class LoadMission {

	private String resourceId;

	private String title;

	private String description;

	protected Integer score;

	private String skillPoints;

	private String answer;

	private String option;

	private Integer type;

	private String category;

	private String writeUp;

}
