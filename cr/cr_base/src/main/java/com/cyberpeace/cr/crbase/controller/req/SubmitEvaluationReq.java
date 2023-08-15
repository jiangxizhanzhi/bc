package com.cyberpeace.cr.crbase.controller.req;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SubmitEvaluationReq {

	@NotNull
	@ApiModelProperty(value = "测评模版ID")
	private String evalTemplateId;

	@ApiModelProperty(value = "房间ID")
	private String roomId;

	@ApiModelProperty(value = "任务提交id")
	private String submitId;

	@ApiModelProperty(value = "评估结果")
	private Integer evaluateStatus;

	@ApiModelProperty(value = "预设原因")
	private Integer reason;

	@ApiModelProperty(value = "裁决详情")
	private String detail;

	@ApiModelProperty(value = "分数改判")
	private Integer score;

	public void setScore(String score) {
		// 处理打分为空字符串的情况
		if (StringUtils.isBlank(score)) {
			this.score = 0;
			return;
		}
		// 校验打分为非零整数
		try {
			this.score = new Integer(score);
			if (this.score < 0) {
				throw new RuntimeException();
			}
		} catch (Exception e) {
			throw new BusinessException(CrBusinessErrorCode.INVALID_SCORE);
		}
	}
}
