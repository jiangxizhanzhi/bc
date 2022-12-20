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
	private String sub_range_id;

	@ApiModelProperty(value = "任务id")
	private String task_id;

	@ApiModelProperty(value = "科目id")
	private String subject_id;

	public String toQuery() {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isBlank(this.task_id)) {
			throw new BusinessException(ErrorCode.EMPTY_TASK_ID);
		} else {
			builder.append("?taskId=");
			builder.append(this.task_id);
		}
		if (StringUtils.isNotBlank(this.sub_range_id)) {
			builder.append("&subRangeId=");
			builder.append(this.sub_range_id);
		}

		if (StringUtils.isNotBlank(this.subject_id)) {
			builder.append("&subjectId=");
			builder.append(this.subject_id);
		}
		return builder.toString();
	}
}
