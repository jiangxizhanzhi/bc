package com.cyberpeace.cr.crbase.controller.rsp;

import lombok.Data;

@Data
public class MissionRsp {

	private String resourceId;

	private String title;

	private String option;

	private Integer type;

	private String category;

	private String description;

	private String writeUp;

	private Boolean showWriteup;

	private String userAnswer;
}
