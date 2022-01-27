package com.cyberpeace.cr.crbase.service.context.mission;

import lombok.Data;

/**
 * @author SN
 */
@Data
public class MissionCategory {

	private String resourceId;

	private String name;

	public MissionCategory(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
