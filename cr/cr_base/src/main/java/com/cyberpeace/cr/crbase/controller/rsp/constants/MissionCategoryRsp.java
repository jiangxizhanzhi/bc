package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionCategoryRsp {

	@ApiModelProperty("类型id")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	public MissionCategoryRsp(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
