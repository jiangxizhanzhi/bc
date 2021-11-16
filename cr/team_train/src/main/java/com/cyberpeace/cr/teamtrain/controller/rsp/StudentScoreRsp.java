package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.teamtrain.service.context.TrainingScore;

import lombok.Data;

@Data
public class StudentScoreRsp {

	private String abilityPercentage;

	private List<Integer> abilityScore;

	private String comment;

	private Integer finalScore;

	private String trainingPercentage;

	private TrainingScore trainingScore;

}