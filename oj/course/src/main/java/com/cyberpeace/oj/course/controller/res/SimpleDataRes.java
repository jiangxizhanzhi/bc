package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单数据结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SimpleDataRes implements Serializable {

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 资源名称
	 */
	@ApiModelProperty("资源名称")
	private String name;

}
