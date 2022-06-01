package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

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

	public void validate() {
		if (stage == null || title == null || process == null) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}
	}

}
