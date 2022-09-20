package com.cyberpeace.rtks.service.bo.xiamen2c;

import java.io.Serializable;

import lombok.Data;

@Data
public class SkillMissionUserContext implements Serializable {

	private String skillId;

	private Integer missionCount;

	private Integer traineeCount;
}
