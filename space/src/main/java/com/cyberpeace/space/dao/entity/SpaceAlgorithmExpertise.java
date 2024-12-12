package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmExpertiseBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@TableName("space_algorithm_expertise")
public class SpaceAlgorithmExpertise extends SpaceBaseTable {

	/**
	 * 课程
	 */
	private String course;

	/**
	 * 百分比
	 */
	private Integer coursePercent;

	/**
	 * 训练
	 */
	private String training;

	/**
	 * 训练百分之
	 */
	private Integer trainingPercent;

	public SpaceAlgorithmExpertiseBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmExpertiseBo bo = BeanUtils.copy(this, SpaceAlgorithmExpertiseBo.class);
		bo.setCourse(this.getListByJson(relatedMap, this.course));
//		bo.setTraining(this.getListByJson(relatedMap, this.training));
		bo.setTraining(JsonUtil.toJsonObjectArray(this.training, IdAndName.class));
		return bo;
	}
}
