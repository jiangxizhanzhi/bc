package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemTemplatePageReq extends PageReqBase {

	@ApiModelProperty("类型")
	private Integer type;
}
