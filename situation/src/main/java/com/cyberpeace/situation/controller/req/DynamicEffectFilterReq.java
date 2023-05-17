package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动效库新增请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class DynamicEffectFilterReq implements Serializable {

	/**
	 * 动效名称
	 */
	@ApiModelProperty(value = "动效名称")
	private String name;

	/**
	 * 动效类型
	 */
	@ApiModelProperty(value = "动效类型")
	private String category;

}
