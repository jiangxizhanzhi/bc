package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号框架
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexFrame {

	/**
	 * 框架计数
	 *
	 * @see IndexFrameCount
	 */
	@ApiModelProperty(value = "计数")
	private IndexFrameCount frameCount;

	/**
	 * 趋势
	 *
	 * @see IndexActionFrameChart
	 */
	@ApiModelProperty(value = "趋势图")
	private IndexActionFrameChart trending;
}
