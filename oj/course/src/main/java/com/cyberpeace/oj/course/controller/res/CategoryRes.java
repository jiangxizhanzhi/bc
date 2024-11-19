package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 类别结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class CategoryRes implements Serializable {

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
    private String resourceId;

	/**
	 * 课程分类名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类名称")
    private String name;

	/**
	 * 课程类型 0=基础 2=攻击 3=防御
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
    private Integer courseType;

}
