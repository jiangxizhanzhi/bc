package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatusOrderContext implements Serializable {

	@JsonProperty("orderId")
	@ApiModelProperty(value = "指令ID")
	private String orderId;

	@JsonProperty("orderType")
	@ApiModelProperty(value = "指令类型")
	private String orderType;

	@JsonProperty("orderTime")
	@ApiModelProperty(value = "指令下达时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	@ApiModelProperty(value = "指令描述")
	private String description;

	@JsonProperty("taskId")
	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@JsonProperty("subjectId")
	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@JsonProperty("faid")
	@ApiModelProperty(value = "方案ID")
	private String faid;

	@JsonProperty("fazt")
	@ApiModelProperty(value = "方案状态")
	private String fazt;

	@JsonProperty("sourceSys")
	@ApiModelProperty(value = "业务系统编号")
	private String sourceSys;

}
