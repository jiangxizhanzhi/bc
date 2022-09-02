package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别
 */
@Data
public class MissionCategory {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public MissionCategory(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
