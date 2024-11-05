
package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间算法设备 BO
 *
 * @author SN
 * @date 2024/11/05
 */
@Data
public class SpaceAlgorithmEquipmentBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "算法名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "装备描述")
	private String description;

	/**
	 * 资产管理
	 */
	@ApiModelProperty(value = "资产管理能力")
	private Integer assetManagement;

	/**
	 * 行为管理
	 */
	@ApiModelProperty(value = "行为管理能力")
	private Integer behaviorManagement;

	/**
	 * 模块隔离
	 */
	@ApiModelProperty(value = "隔离模块能力")
	private Integer moduleIsolation;

	/**
	 * 风险发现
	 */
	@ApiModelProperty(value = "风险发现能力")
	private Integer riskDiscovery;

	/**
	 * 威胁监控
	 */
	@ApiModelProperty(value = "威胁监测能力")
	private Integer threatMonitoring;

	/**
	 * 策略
	 */
	@ApiModelProperty(value = "策略能力")
	private Integer strategy;

	/**
	 * 能力
	 */
	@ApiModelProperty(value = "性能")
	private Integer capability;

	public SpaceAlgorithmEquipment bo2Entity() {
		SpaceAlgorithmEquipment entity = BeanUtils.copy(this, SpaceAlgorithmEquipment.class);
		return entity;
	}

}
