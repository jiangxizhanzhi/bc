package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskStepConfig {

	@ApiModelProperty("步骤阶段")
	private Integer stage;

	@ApiModelProperty("步骤标题")
	private String title;

	@ApiModelProperty("步骤过程")
	private String process;

}
