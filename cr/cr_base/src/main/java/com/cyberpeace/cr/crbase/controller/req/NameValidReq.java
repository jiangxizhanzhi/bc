package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名称有效请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class NameValidReq {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模板名称")
	String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类型")
	Integer type;

	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "resource_id，模板编号")
	String id;

}
