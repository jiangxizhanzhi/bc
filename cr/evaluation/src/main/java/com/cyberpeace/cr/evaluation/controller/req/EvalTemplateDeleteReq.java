package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTemplateDeleteReq {

	@ApiModelProperty(value = "靶场测评模板Ids", required = true)
	@NotNull
	private List<String> templateIds;

}