package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainPlanBo implements Serializable {

	@ApiModelProperty("训练计划编号")
	private String resourceId;

	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	@ApiModelProperty("姓名")
	private String username;

	@ApiModelProperty("类型, 1=普通，2=连续")
	private Integer type;

}
