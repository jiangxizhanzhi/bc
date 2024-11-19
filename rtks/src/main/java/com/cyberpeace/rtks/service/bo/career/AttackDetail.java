package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击详情
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AttackDetail implements Serializable {

	/**
	 * 阶段id
	 *
	 * @see String
	 */
	@ApiModelProperty("阶段id")
	private String categoryId;

	/**
	 * 阶段名称
	 *
	 * @see String
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 技术id
	 *
	 * @see String
	 */
	@ApiModelProperty("技术id")
	private String resourceId;

	/**
	 * 技术名称
	 *
	 * @see String
	 */
	@ApiModelProperty("技术名称")
	private String name;

	/**
	 * 关联模板
	 *
	 * @see List<BusinessTemplateSimple>
	 */
	@ApiModelProperty("关联模板")
	private List<BusinessTemplateSimple> templates;
}
