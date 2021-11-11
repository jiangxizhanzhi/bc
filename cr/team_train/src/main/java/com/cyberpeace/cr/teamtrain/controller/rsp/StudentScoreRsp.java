package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.teamtrain.service.context.TrainingScore;

import lombok.Data;

@Data
public class StudentScoreRsp {

	String abilityPercentage;

	List<Integer> abilityScore;

	String comment;

	Integer finalScore;

	String trainingPercentage;

	TrainingScore trainingScore;

}