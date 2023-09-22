package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

@Data
public class TrainingRaw implements Serializable {

	private String businessTemplateId;

	private String trainingId;

	private String trainingName;

	private Integer durationTime;

	private Integer difficulty;

	private String preSkill;

	private String trainSkill;

	private Integer trainingStatus;

	private String logo;

	private Integer trainingType;

	private Integer roleGroup;

}
