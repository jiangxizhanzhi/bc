package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskTrendContext implements Serializable {

	@ApiModelProperty("时间")
	private Date time;

	@ApiModelProperty("任务数")
	private Integer count;

}
