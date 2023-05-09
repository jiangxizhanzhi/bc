package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟分页列表元素
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelBo extends UserModelBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;

	/**
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型名称")
	private String userType;

	/**
	 * 行为数量
	 */
	@ApiModelProperty(value = "行为数量")
	private Integer behaviorCount;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "访问开始时间")
	private LocalTime startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "访问结束时间")
	private LocalTime endTime;

	/**
	 * 是否限制带宽
	 */
	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	/**
	 * 带宽
	 */
	@ApiModelProperty(value = "带宽(Mbps)")
	private Integer bandwidth;
}
