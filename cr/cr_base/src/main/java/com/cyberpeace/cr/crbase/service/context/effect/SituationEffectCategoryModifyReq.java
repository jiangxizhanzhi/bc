package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectCategoryModifyReq extends SituationEffectCategoryAddReq {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
