package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟详情
 */
@Data
public class UserModelDetail extends UserModelBase implements Serializable {

	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;

	@ApiModelProperty(value = "简介")
	private String description;

	@ApiModelProperty(value = "用户类型名称")
	private String userType;

	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	@ApiModelProperty(value = "访问带宽")
	private Integer bandwidth;

	@ApiModelProperty(value = "访问行为")
	private List<Behavior> behaviors;
}
