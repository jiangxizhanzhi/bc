package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author SN
 */
@Data
public class MissionCategoryRes implements Serializable {

	@ApiModelProperty("类型id")
	private String resourceId;

	@ApiModelProperty("中文名称")
	private String name;

	@ApiModelProperty("英文名称")
	private String key;
}
