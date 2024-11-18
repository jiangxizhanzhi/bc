package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务类别结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionCategoryRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("类型id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 钥匙
	 *
	 * @see String
	 */
	@ApiModelProperty("英文名称")
	private String key;
}
