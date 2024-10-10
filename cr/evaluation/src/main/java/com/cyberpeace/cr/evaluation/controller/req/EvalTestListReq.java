package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTestListReq extends PageReqBase {

	@ApiModelProperty("训练类型")
	private Integer type;

	@ApiModelProperty("难度")
	private Integer difficult;

	@ApiModelProperty("训练状态")
	private Integer status;
}
