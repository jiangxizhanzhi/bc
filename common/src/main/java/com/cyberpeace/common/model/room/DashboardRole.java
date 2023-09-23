package com.cyberpeace.common.model.room;

import java.io.Serializable;

import lombok.Data;

@Data
public class DashboardRole implements Serializable {

	private String roleType;

	private String role;

	private Integer roleCode;
}
