package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserRoleContext implements Serializable {

	private String userId;

	private String roleId;

	private String roleName;

	private String levelId;
}
