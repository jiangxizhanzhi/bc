package com.cyberpeace.situation.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicParameterContext extends StatusContext {

	@ApiModelProperty(value = "大屏ID")
	private String dashboardId;

	@ApiModelProperty(value = "特征ID")
	private String featureId;

	@ApiModelProperty(value = "大屏角色")
	private String role;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "大屏当前天文时间")
	private Date currentTime;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

}
