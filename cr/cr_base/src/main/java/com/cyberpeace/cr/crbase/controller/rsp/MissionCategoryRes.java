package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别返回值
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class MissionCategoryRes implements Serializable {

	@ApiModelProperty("类型id")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("英文名称")
	private String key;
}
