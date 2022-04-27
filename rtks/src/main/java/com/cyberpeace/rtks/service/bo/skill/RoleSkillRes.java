package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleSkillRes implements Serializable {

	private String roleId;

	private Integer skillCount;

	private Integer missionCount;
}
