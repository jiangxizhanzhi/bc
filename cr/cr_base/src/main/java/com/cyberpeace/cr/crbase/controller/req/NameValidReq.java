package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名字校验请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class NameValidReq {

	@ApiModelProperty(value = "模板名称")
	String name;

	@ApiModelProperty(value = "训练类型")
	Integer type;

	@ApiModelProperty(value = "resource_id，模板编号")
	String id;

}
