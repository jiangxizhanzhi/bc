package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.teamtrain.service.context.TrainingScore;

import lombok.Data;

@Data
public class StudentScoreRsp {

	String ability_percentage;

	List<Integer> ability_score;

	String comment;

	Integer final_score;

	String training_percentage;

	TrainingScore trainingScore;

}