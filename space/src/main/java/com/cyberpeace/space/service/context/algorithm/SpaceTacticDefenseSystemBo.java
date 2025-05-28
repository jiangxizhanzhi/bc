
package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间战法防御系统Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceTacticDefenseSystemBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "阶段，type=48")
	private Integer stage;

	@ApiModelProperty(value = "技战法定义")
	private String definition;

	public SpaceTacticDefenseSystem bo2Entity() {
		SpaceTacticDefenseSystem entity = BeanUtils.copy(this, SpaceTacticDefenseSystem.class);
		return entity;
	}

}
