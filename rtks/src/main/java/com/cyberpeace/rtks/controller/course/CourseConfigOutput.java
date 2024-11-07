package com.cyberpeace.rtks.controller.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程配置输出
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CourseConfigOutput {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

}
