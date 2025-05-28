
package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间算法个人Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmIndividualBo extends SpaceBoBase {

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
	public SpaceAlgorithmIndividual bo2Entity() {
		SpaceAlgorithmIndividual entity = BeanUtils.copy(this, SpaceAlgorithmIndividual.class);
		return entity;
	}

}
    