package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

@Data
public class MissionContext implements Serializable {

	private String missionTemplateId;

	private String title;

	private String businessTemplateId;

	private String skillId;

	private String skillContent;
}
