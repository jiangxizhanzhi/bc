package com.cyberpeace.cr.crbase.controller.req;

import lombok.Data;

@Data
public class StudentScoreReq {

	private String abilityPercentage;

	private String abilityScore;

	private String comment;

	private Integer finalScore;

	private String trainingPercentage;

	private String roomId;

	private String user;
}
