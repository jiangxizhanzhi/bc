package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskEventContext implements Serializable {

	@ApiModelProperty("责任靶场ID")
	private String responsibleRangeId;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("科目名称")
	private String subjectName;

	@ApiModelProperty("任务模式")
	private String taskMode;

	@ApiModelProperty("任务操作")
	private String taskEvent;

	@ApiModelProperty("下发站点类型")
	private String sourceType;

	@ApiModelProperty("下发站点ID")
	private String sourceId;

	@ApiModelProperty("接收站点列表")
	private List<TargetSiteContext> targetSites;

	@ApiModelProperty("事件描述")
	private String eventDesc;

	@ApiModelProperty("事件时间")
	private String eventTime;

}
