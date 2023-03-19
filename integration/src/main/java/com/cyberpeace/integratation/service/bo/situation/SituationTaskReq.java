package com.cyberpeace.integratation.service.bo.situation;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationTaskReq implements Serializable {

	@ApiModelProperty(value = "分靶场id")
	private String subRangeId;

	@ApiModelProperty(value = "任务id")
	private String taskId;

	@ApiModelProperty(value = "科目id")
	private String subjectId;

	public String toQuery() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isBlank(this.taskId)) {
			throw new BusinessException(ErrorCode.EMPTY_TASK_ID);
		} else {
			builder.append("?taskId=");
			builder.append(this.taskId);
		}
		if (StringUtils.isNotBlank(this.subRangeId)) {
			builder.append("&subRangeId=");
			builder.append(this.subRangeId);
		}

		if (StringUtils.isNotBlank(this.subjectId)) {
			builder.append("&subjectId=");
			builder.append(this.subjectId);
		}
		return builder.toString();
	}
}
