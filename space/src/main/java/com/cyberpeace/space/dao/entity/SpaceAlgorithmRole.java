package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmRoleBo;
import lombok.Data;

import java.util.Map;


@Data
@TableName("space_algorithm_role")
public class SpaceAlgorithmRole extends SpaceBaseTable {

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
	public SpaceAlgorithmRoleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmRoleBo bo = BeanUtils.copy(this, SpaceAlgorithmRoleBo.class);
		bo.setCourse(this.getListByJson(relatedMap, this.course));
		bo.setTraining(this.getListByJson(relatedMap, this.training));
		return bo;
    }
}
    