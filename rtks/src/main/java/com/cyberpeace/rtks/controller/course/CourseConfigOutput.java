package com.cyberpeace.rtks.controller.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程配置输出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class CourseConfigOutput {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

}
