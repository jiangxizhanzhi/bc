package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author SN
 * @date 2023/09/14
 */
@Data
public class EntranceBo {

	@ApiModelProperty(value = "是否显示入口")
	private Boolean show;

	@ApiModelProperty(value = "入口链接")
	private String url;

	@ApiModelProperty(value = "ws链接")
	private String wsUrl;
}
