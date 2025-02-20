package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class EvalTaskStepConfig {

	@ApiModelProperty("步骤阶段")
	private Integer stage;

	@ApiModelProperty("步骤标题")
	private String title;

	@ApiModelProperty("步骤过程")
	private String process;

	@ApiModelProperty("装备")
	private List<ToolBo> equip;

	@ApiModelProperty("附件名称")
	private String attachmentName;

	@ApiModelProperty("附件")
	private String attachmentPath;

	public void validate() {
		if (stage == null || title == null || process == null) {
			log.error("The configuration of the evaluation step is incorrect! EvalTaskStepConfig = [{}]", JsonUtil.toJsonString(this));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);
	}

}
