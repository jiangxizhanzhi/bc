package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

@Data
public class LevelRoleContext implements Serializable {

	private String levelId;

	private String levelName;

	private String roleId;

	private String roleName;

}
