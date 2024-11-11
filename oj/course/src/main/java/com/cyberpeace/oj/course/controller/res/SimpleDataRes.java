package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 简单数据结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SimpleDataRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("资源名称")
	private String name;

}
