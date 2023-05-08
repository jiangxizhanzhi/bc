package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

/**
 * 带行为变量的用户模型
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelWithVariable implements Serializable {

	/**
	 * 用户模型id
	 */
	@ApiModelProperty(value = "用户模型id")
	private String userModelId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String userName;

	/**
	 * 是否限制带宽
	 */
	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	/**
	 * 带宽
	 */
	@ApiModelProperty(value = "带宽限制")
	private Integer bandwidth;

	/**
	 * 行为列表
	 */
	@ApiModelProperty(value = "行为列表")
	private List<BehaviorWithVariable> behaviorList = Lists.newArrayList();
}
