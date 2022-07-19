package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleMatchRecordContext implements Serializable {

	private String userId;

	private String roleId;

	private String roleName;

	private String levelId;

	private Integer match;
}
