package com.cyberpeace.cr.teamtrain.controller.req;

import lombok.Data;

@Data
public class StudentScoreReq {

	String abilityPercentage;

	String abilityScore;

	String comment;

	Integer finalScore;

	String trainingPercentage;

	String trainingTeam;

	String user;
}
