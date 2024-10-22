package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TacticDefenseCategoryAddReq {

	private String name;
    @ApiModelProperty(value = "战术阶段，常量type=48")
	private Integer stage;

	private String cover;
}
