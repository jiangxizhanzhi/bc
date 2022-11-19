package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskEventContext implements Serializable {

	@ApiModelProperty("责任靶场ID")
	private String responsibleRangeId;

	@ApiModelProperty("责任靶场名称")
	private String responsibleRangeName;

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

	@ApiModelProperty("科目操作")
	private String subjectEvent;

	@ApiModelProperty("下发站点类型")
	private Integer sourceType;

	@ApiModelProperty("下发站点ID")
	private String sourceId;

	@ApiModelProperty("下发站点名称")
	private String sourceName;

	@ApiModelProperty("接收站点列表")
	private List<TargetSiteContext> targetSites;

	@ApiModelProperty("事件描述")
	private String eventDesc;

	@ApiModelProperty("事件时间")
	private Date eventTime;

	@ApiModelProperty("事件ID")
	private String eventId;

	public TaskEventContext() {
		this.eventDesc = "ZH性分靶场 开始执行 0717红蓝对抗训练任务";
		this.eventTime = new Date();
		this.responsibleRangeId = "zhxfbc";
		this.responsibleRangeName = "ZH性分靶场";
		this.taskId = "subject_001";
		this.taskName = "0717红蓝对抗训练任务";
		this.taskMode = "独立任务";
		this.taskEvent = "开始";
		this.sourceType = 1;
		this.sourceId = "lgzx";
		this.sourceName = "LG中心";
		this.targetSites = Lists.newArrayList();
		this.targetSites.add(new TargetSiteContext(2, "zhxfbc", "ZH性分靶场"));
	}

}
