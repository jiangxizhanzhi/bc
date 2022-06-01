package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateIdRsp implements Serializable {

	@ApiModelProperty(value = "测评靶场模板id")
	private String templateId;

	public EvalTemplateIdRsp(String templateId) {
		this.templateId = templateId;
	}

}
