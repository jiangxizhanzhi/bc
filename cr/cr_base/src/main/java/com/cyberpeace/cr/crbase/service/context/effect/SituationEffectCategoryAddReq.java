package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectCategoryAddReq {

	@ApiModelProperty(value = "名称")
	private String name;
}
