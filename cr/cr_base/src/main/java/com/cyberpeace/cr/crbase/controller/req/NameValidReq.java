package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名称有效请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class NameValidReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "模板名称")
	String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "训练类型")
	Integer type;

	/**
	 * id
	 */
	@ApiModelProperty(value = "resource_id，模板编号")
	String id;

}
