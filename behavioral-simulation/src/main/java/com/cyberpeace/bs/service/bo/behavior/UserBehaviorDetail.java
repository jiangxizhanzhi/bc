package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟详情
 */
@Data
public class UserBehaviorDetail extends UserBehaviorBase implements Serializable {

	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;

	@ApiModelProperty(value = "简介")
	private String description;

	@ApiModelProperty(value = "访问带宽")
	private Integer bandwidth;

	@ApiModelProperty(value = "访问行为")
	private List<Behavior> behaviors;
}
