package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 编号行动框架图表
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexActionFrameChart {

	/**
	 * x 日期
	 *
	 * @see List<String>
	 */
	private List<String> xDates = Lists.newArrayList();

	/**
	 * 过程
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "面向攻击流程")
	private List<Integer> process = Lists.newArrayList();

	/**
	 * 目标
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "面向攻击目标")
	private List<Integer> target = Lists.newArrayList();

}
