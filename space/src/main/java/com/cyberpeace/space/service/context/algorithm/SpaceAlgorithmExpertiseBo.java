
package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmExpertise;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SpaceAlgorithmExpertiseBo extends SpaceBoBase {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 课程
	 */
	@ApiModelProperty(value = "课程列表")
	private List<IdAndName> course;
	/**
	 * 百分比
	 */
	@ApiModelProperty(value = "课程占比")
	private Integer coursePercent;
	/**
	 * 训练
	 */
	@ApiModelProperty(value = "训练列表")
	private List<IdAndName> training;

	/**
	 * 训练百分之
	 */
	@ApiModelProperty(value = "训练占比")
	private Integer trainingPercent;

	public SpaceAlgorithmExpertise bo2Entity() {
		SpaceAlgorithmExpertise entity = BeanUtils.copy(this, SpaceAlgorithmExpertise.class);
		entity.setCourse(this.getIds(this.course));
//		entity.setTraining(this.getIds(this.training));
		entity.setTraining(JsonUtil.arrayToString(this.training));
		return entity;
	}

}
    