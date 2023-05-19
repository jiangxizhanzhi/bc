package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import lombok.Data;

@Data
public class EvaluationMissionBo implements Serializable {

	private String resourceId;

	private String title;

	private String description;

	private String userAnswer;

	private String answer;

	private Integer type;

	private String option;

	private String skillPoints;

	private String writeUp;

	private Integer score;
}
