package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.TrainingScore;

import lombok.Data;

@Data
public class StudentScoreRsp {

	private String abilityPercentage = "";

	private List<Integer> abilityScore = new ArrayList<>();

	private String comment = "";

	private Integer finalScore = 0;

	private String trainingPercentage = "";

	private TrainingScore trainingScore = new TrainingScore();

}