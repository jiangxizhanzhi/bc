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
 * @date 2024/11/07
 */
@Data
public class AttackDetail implements Serializable {

	/**
	 * 类别id
	 */
	@ApiModelProperty("阶段id")
	private String categoryId;

	/**
	 * 类别名称
	 */
	@ApiModelProperty("阶段名称")
	private String categoryName;

	/**
	 * 资源id
	 */
	@ApiModelProperty("技术id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("技术名称")
	private String name;

	/**
	 * 模板
	 */
	@ApiModelProperty("关联模板")
	private List<BusinessTemplateSimple> templates;
}
