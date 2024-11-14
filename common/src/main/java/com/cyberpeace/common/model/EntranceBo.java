package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author SN
 * @date 2023/09/14
 */
@Data
public class EntranceBo {

	/**
	 * 是否显示入口
	 */
	@ApiModelProperty(value = "是否显示入口")
	private Boolean show;

	/**
	 * 入口链接
	 */
	@ApiModelProperty(value = "入口链接")
	private String url;

	/**
	 * ws链接
	 */
	@ApiModelProperty(value = "ws链接")
	private String wsUrl;
}
