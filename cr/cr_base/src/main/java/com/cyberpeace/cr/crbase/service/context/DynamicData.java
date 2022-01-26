package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import lombok.Data;

@Data
public class DynamicData {

	private Integer actionType;

	private Date actionTime;

	private String missionName;
}
