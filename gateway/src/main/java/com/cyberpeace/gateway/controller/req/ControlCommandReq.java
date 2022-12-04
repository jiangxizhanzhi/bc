package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ControlCommandReq implements Serializable {

	@ApiModelProperty(value = "数据库主键")
	private Long id;

	@ApiModelProperty(value = "源系统编号")
	private String sourceSystemId;

	@ApiModelProperty(value = "目的系统编号")
	private String targetSystemId;

	@ApiModelProperty(value = "指令ID")
	private String orderId;

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "科目ID")
	private String subjectId;

	@ApiModelProperty(value = "指令类型")
	private String orderType;

	@ApiModelProperty(value = "指令下达时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderTime;

	@ApiModelProperty(value = "指令描述")
	private String description;

	@ApiModelProperty(value = "任务唯一识别ID")
	private String seqId;

	@ApiModelProperty(value = "入库时间")
	private Date createTime;

	@ApiModelProperty(value = "子模块ID")
	private String moduleId;

	public void setCreateTime(Long createTime) {
		this.createTime = new Date(createTime);
	}

}
