package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

@Data
public class UserModelWithVariable implements Serializable {

	@ApiModelProperty(value = "用户模型id")
	private String userModelId;

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	@ApiModelProperty(value = "带宽限制")
	private Integer bandwidth;

	@ApiModelProperty(value = "行为列表")
	private List<BehaviorWithVariable> behaviorList = Lists.newArrayList();
}
