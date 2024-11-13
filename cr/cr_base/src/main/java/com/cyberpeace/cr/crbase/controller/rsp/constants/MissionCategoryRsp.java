package com.cyberpeace.cr.crbase.controller.rsp.constants;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class MissionCategoryRsp implements Serializable {

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
