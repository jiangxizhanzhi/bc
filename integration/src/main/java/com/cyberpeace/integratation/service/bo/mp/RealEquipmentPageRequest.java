package com.cyberpeace.integratation.service.bo.mp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEquipmentPageRequest {

	/**
	 * 系统主类型
	 */
	@ApiModelProperty(value = "一级类型")
	private Integer category;

	/**
	 * 系统子类型
	 */
	@ApiModelProperty(value = "二级类型")
	private Integer secondCategory;

	/**
	 * 系统子类型
	 */
	@ApiModelProperty(value = "三级类型")
	private Integer thirdCategory;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 在位状态
	 */
	@ApiModelProperty(value = "在位状态")
	private Integer inPosition;

	/**
	 * 使用状态
	 */
	@ApiModelProperty(value = "使用状态")
	private Integer useStatus;

	/**
	 * 精确查找名称
	 */
	@ApiModelProperty(value = "精确查找名称")
	private String exactName;

	private Integer page;

	private Integer perPage;
}
