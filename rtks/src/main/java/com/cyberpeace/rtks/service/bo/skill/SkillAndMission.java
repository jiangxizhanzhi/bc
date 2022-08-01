package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

@Data
public class SkillAndMission implements Serializable {

	private String skillId;

	private String missionId;
}
