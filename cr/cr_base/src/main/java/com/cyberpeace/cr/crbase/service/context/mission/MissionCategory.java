package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别
 *
 * @author cyberpeace
 * @date 2023/05/06
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
