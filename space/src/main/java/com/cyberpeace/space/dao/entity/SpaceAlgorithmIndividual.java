package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmIndividualBo;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_algorithm_individual")
public class SpaceAlgorithmIndividual extends SpaceBaseTable {

	/**
	 * 专业知识百分比
	 */
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	private Integer rolePercent;

	public SpaceAlgorithmIndividualBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmIndividualBo bo = BeanUtils.copy(this, SpaceAlgorithmIndividualBo.class);
		return bo;
	}
}
