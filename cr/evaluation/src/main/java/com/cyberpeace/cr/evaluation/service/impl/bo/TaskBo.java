package com.cyberpeace.cr.evaluation.service.impl.bo;

import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskBo extends EvalMissionTemplate {

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("任务id")
	private String resourceId;

	@ApiModelProperty("超时状态")
	private Integer overtimeStatus;
}
