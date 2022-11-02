package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟分页列表元素
 */
@Data
public class UserBehaviorBo extends UserBehaviorBase implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "行为数量")
	private Integer behaviorCount;

	@ApiModelProperty(value = "访问开始时间")
	private LocalTime startTime;

	@ApiModelProperty(value = "访问结束时间")
	private LocalTime endTime;
}
