package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效类别修改请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectCategoryModifyReq extends SituationEffectCategoryAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;
}
