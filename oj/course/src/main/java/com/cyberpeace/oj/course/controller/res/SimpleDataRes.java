package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单数据结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SimpleDataRes implements Serializable {

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 资源名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;

}
