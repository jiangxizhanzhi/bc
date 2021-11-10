package com.cyberpeace.cr.crbase.controller.rsp;

import lombok.Data;

@Data
public class MissionRsp {

	String resourceId;

	String title;

	String option;

	Integer type;

	String category;

	String description;

	String writeUp;

	Boolean showWriteup;

	String userAnswer;
}
