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

	/**
	 * 探针
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "探测类工具")
	private Long probe = 0L;

	/**
	 * 攻击
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "攻击类工具")
	private Long attack = 0L;

	/**
	 * 控制
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "控制类工具")
	private Long control = 0L;

	/**
	 * 防御
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "防御类工具")
	private Long defense = 0L;

	/**
	 * 其他
	 *
	 * @see Long
	 */
	@ApiModelProperty(value = "其他类工具")
	private Long other = 0L;


	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 平台
	 *
	 * @see Map<String, Integer>
	 */
	@ApiModelProperty(value = "平台统计")
	private Map<String, Integer> platform;
}
