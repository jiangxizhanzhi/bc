package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ControlCommandReq implements Serializable {

	@ApiModelProperty(value = "数据库主键")
	private Long id;

	@ApiModelProperty(value = "源系统编号")
	@JsonProperty("sourceSystemId")
	private String sourceSystemId;

	@JsonProperty("targetSystemId")
	@ApiModelProperty(value = "目的系统编号")
	private String targetSystemId;

	@JsonProperty("orderId")
	@ApiModelProperty(value = "指令ID")
	private String orderId;

	@JsonProperty("taskId")
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@JsonProperty("subjectId")
	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@JsonProperty("orderType")
	@ApiModelProperty(value = "指令类型")
	private String orderType;

	@JsonProperty("orderTime")
	@ApiModelProperty(value = "指令下达时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	@ApiModelProperty(value = "指令描述")
	private String description;

	@JsonProperty("seqId")
	@ApiModelProperty(value = "任务唯一识别ID")
	private String seqId;

	@JsonProperty("timeOperation")
	@ApiModelProperty(value = "ZZ开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeOperation;

	@JsonProperty("timeStandard")
	@ApiModelProperty(value = "天文开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timeStandard;

	@JsonProperty("timeFactor")
	@ApiModelProperty(value = "时间倍率")
	private String timeFactor;

	@JsonProperty("createTime")
	@ApiModelProperty(value = "入库时间")
	private Date createTime;

	@JsonProperty("moduleId")
	@ApiModelProperty(value = "子模块ID")
	private String moduleId;

	public void setCreateTime(Long createTime) {
		this.createTime = new Date(createTime);
	}

}
