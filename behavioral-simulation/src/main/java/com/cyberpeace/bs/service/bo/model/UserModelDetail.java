package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.Behavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户行为模拟详情
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelDetail extends UserModelBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "用户行为编号")
	private String resourceId;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "简介")
	private String description;

	/**
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型名称")
	private String userType;

	/**
	 * 是否限制带宽
	 */
	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	/**
	 * 带宽
	 */
	@ApiModelProperty(value = "访问带宽(Mbps)")
	private Integer bandwidth;

	/**
	 * 行为数据列表
	 */
	@ApiModelProperty(value = "访问行为")
	private List<Behavior> behaviors;

	/**
	 * 图表数据
	 */
	@ApiModelProperty(value = "折线图、饼图")
	private ChartResult chart;
}
