package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectCategoryName {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
}
