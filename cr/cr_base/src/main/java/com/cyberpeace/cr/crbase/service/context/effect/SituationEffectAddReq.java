package com.cyberpeace.cr.crbase.service.context.effect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationEffectAddReq {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "缩略图")
	private String logo;

	@ApiModelProperty(value = "分类id")
	private String categoryId;

	@ApiModelProperty(value = "动效文件")
	private String file;
}
