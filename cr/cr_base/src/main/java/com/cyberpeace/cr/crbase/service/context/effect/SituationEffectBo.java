package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectBo {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "缩略图")
	private String logo;

	@ApiModelProperty(value = "分类id")
	private String categoryId;
}
