package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MissionCategory {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	public MissionCategory(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
