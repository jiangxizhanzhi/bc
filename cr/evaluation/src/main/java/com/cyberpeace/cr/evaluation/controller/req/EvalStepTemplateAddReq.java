package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class EvalStepTemplateAddReq implements Serializable {

	@ApiModelProperty(value = "云模板id", required = true)
	@NotBlank
	private String cloudTemplateId;

	@ApiModelProperty(value = "阶段名称", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "验证过程", required = true)
	@NotBlank
	private String process;

	@ApiModelProperty("载荷设置")
	private List<LoadSetReq> set;
}
