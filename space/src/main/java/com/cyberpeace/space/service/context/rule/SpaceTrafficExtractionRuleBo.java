
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量萃取统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceTrafficExtractionRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 次序
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "顺序")
	private String order;

	// @ApiModelProperty(value = "文件名")
	// private String filename;
	//
	// @ApiModelProperty(value = "路径")
	// private String route;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具")
	private String tool;


	/**
	 * BO2 实体
	 *
	 * @return 空间流量萃取统治
	 * @throws
	 * @see SpaceTrafficExtractionRule
	 */
	public SpaceTrafficExtractionRule bo2Entity() {
		SpaceTrafficExtractionRule entity = BeanUtils.copy(this, SpaceTrafficExtractionRule.class);
		return entity;
	}

}
    