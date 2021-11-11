package com.cyberpeace.cr.teamtrain.controller.req;

import lombok.Data;

@Data
public class StudentScoreReq {

	String ability_percentage;

	String ability_score;

	String comment;

	Integer final_score;

	String training_percentage;

	String training_team;

	String user;
}
