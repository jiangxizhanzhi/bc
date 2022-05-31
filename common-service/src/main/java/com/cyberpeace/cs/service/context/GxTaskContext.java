package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GxTaskContext implements Serializable {

	@ApiModelProperty(value = "训练id")
	private String taskId;

	@ApiModelProperty(value = "训练名称")
	private String name;

	@ApiModelProperty(value = "训练内容")
	private String content;

	@ApiModelProperty(value = "训练目标")
	private String target;

	@ApiModelProperty(value = "训练用户列表")
	private String trainees;

	@ApiModelProperty(value = "训练用户人数")
	private Integer traineesCount;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "训练时长(小时)")
	private Double duration;

	@ApiModelProperty(value = "任务有效期（天）")
	private Integer validityPeriod;

}