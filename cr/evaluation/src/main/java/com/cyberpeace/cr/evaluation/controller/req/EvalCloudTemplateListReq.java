package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalCloudTemplateListReq extends PageReqBase implements Serializable {

	@ApiModelProperty("试验类型")
	private Integer type;
}
