package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class CategoryAddReq implements Serializable {

	@ApiModelProperty("中文名称")
	@NotBlank
	private String cnName;

	@ApiModelProperty("英文名称")
	@NotBlank
	private String enName;

	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	@NotBlank
	private String type;
}
