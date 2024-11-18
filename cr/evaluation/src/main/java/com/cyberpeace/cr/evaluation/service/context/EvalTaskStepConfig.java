package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * eval 任务步骤配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@Slf4j
public class EvalTaskStepConfig {

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty("步骤阶段")
	private Integer stage;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 过程
	 *
	 * @see String
	 */
	@ApiModelProperty("步骤过程")
	private String process;

	public void validate() {
		if (stage == null || title == null || process == null) {
			log.error("The configuration of the evaluation step is incorrect! EvalTaskStepConfig = [{}]", JsonUtil.toJsonString(this));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);
	}

}
