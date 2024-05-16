package com.cyberpeace.cr.crbase.service.context.effect;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectPageReq extends PageReqBase {

	@ApiModelProperty(value = "分类id")
	private String categoryId;
}
