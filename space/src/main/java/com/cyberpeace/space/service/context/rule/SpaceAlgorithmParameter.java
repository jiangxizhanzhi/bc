package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space 算法参数
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAlgorithmParameter {

	@ApiModelProperty(value = "参数名称")
	private String name;

	@ApiModelProperty(value = "参数类型")
	private Integer type;

	@ApiModelProperty(value = "默认值")
	private String defaultValue;

	@ApiModelProperty(value = "参数说明")
	private String description;
}
