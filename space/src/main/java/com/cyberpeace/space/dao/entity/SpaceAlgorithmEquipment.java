package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmEquipmentBo;
import lombok.Data;

import java.util.Map;


@Data
@TableName("space_algorithm_equipment")
public class SpaceAlgorithmEquipment extends SpaceBaseTable {

	/**
	 * 描述
	 */
	private String description;
	/**
	 * 资产管理
	 */
	private Integer assetManagement;
	/**
	 * 行为管理
	 */
	private Integer behaviorManagement;
	/**
	 * 模块隔离
	 */
	private Integer moduleIsolation;
	/**
	 * 风险发现
	 */
	private Integer riskDiscovery;
	/**
	 * 威胁监控
	 */
	private Integer threatMonitoring;
	/**
	 * 策略
	 */
	private Integer strategy;
	/**
	 * 能力
	 */
	private Integer capability;

	public SpaceAlgorithmEquipmentBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmEquipmentBo bo = BeanUtils.copy(this, SpaceAlgorithmEquipmentBo.class);
		return bo;
    }
}
    