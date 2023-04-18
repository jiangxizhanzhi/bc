package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmissionReq implements Serializable {

	@JsonProperty("taskId")
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@JsonProperty("subjectId")
	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@JsonProperty("schemeId")
	@ApiModelProperty(value = "方案ID")
	private String schemeId;

	@JsonProperty("orderType")
	@ApiModelProperty(value = "指令类型")
	private String orderType;

	@JsonProperty("orderValue")
	@ApiModelProperty(value = "指令值")
	private String orderValue;

	@JsonProperty("orderTime")
	@ApiModelProperty(value = "指令下达时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

}
