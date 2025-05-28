package com.cyberpeace.space.service.context.index;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTool {

	@ApiModelProperty(value = "探测类工具")
	private Long probe = 0L;

	@ApiModelProperty(value = "攻击类工具")
	private Long attack = 0L;

	@ApiModelProperty(value = "控制类工具")
	private Long control = 0L;

	@ApiModelProperty(value = "防御类工具")
	private Long defense = 0L;

	@ApiModelProperty(value = "其他类工具")
	private Long other = 0L;


	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	@ApiModelProperty(value = "平台统计")
	private Map<String, Integer> platform;
}
