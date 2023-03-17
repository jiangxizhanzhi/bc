package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationDbAccessReq extends PageReqBase implements Serializable {

	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	@ApiModelProperty(value = "表名称")
	private String tableName;

	@ApiModelProperty(value = "操作类型")
	private String operationType;

}
