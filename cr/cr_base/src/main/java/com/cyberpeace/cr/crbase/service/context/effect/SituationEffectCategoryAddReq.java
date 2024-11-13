package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效类别新增请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectCategoryAddReq {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;
}
