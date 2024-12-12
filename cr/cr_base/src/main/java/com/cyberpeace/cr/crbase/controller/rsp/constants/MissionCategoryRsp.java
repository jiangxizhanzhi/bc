package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务类别响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionCategoryRsp {

	/**
	 * 资源id
	 */
	@ApiModelProperty("类型id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	public MissionCategoryRsp(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}
