package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalStepTemplateAddReq implements Serializable {

	@ApiModelProperty("云模板id")
	private String cloudTemplateId;

	@ApiModelProperty("阶段名称")
	private String name;

	@ApiModelProperty("验证过程")
	private String process;

	@ApiModelProperty("载荷设置")
	private List<LoadSetReq> set;
}
