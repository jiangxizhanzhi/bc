
package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpaceAlgorithmTeamBo extends SpaceBoBase {

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
	 * 专业知识百分比
	 */
	@ApiModelProperty(value = "专业能力占比")
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	@ApiModelProperty(value = "岗位能力占比")
	private Integer rolePercent;

	public SpaceAlgorithmTeam bo2Entity() {
		SpaceAlgorithmTeam entity = BeanUtils.copy(this, SpaceAlgorithmTeam.class);
		return entity;
	}

}
    