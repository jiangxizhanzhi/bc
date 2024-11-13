package com.cyberpeace.cr.tool.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 战术工具
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TacticTool {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "工具类型")
	private String category;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型")
	private String categoryName;

	/**
	 * 工具类型名称
	 */
	@ApiModelProperty(value = "工具类型名称")
	private String categoryI18nName;

	/**
	 * 介绍
	 */
	private String introduction;
}
