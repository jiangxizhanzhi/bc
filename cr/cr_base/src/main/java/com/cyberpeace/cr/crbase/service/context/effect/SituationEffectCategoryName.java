package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效类别名称
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectCategoryName {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
}
