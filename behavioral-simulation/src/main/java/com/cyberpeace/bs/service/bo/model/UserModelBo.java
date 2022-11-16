package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟分页列表元素
 */
@Data
public class UserModelBo extends UserModelBase implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "用户类型名称")
	private String userType;

	@ApiModelProperty(value = "行为数量")
	private Integer behaviorCount;

	@ApiModelProperty(value = "访问开始时间")
	private LocalTime startTime;

	@ApiModelProperty(value = "访问结束时间")
	private LocalTime endTime;

	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	@ApiModelProperty(value = "带宽(Mbps)")
	private Integer bandwidth;
}
