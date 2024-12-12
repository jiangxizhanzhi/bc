package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务类别结果
 *
 * @author SN@date 2024/11/08
 */
@Getter
@Setter
public class MissionCategoryRes implements Serializable {

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

	/**
	 * 钥匙
	 */
	@ApiModelProperty("英文名称")
	private String key;
}
