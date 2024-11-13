package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效修改请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectModifyReq extends SituationEffectAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;
}
