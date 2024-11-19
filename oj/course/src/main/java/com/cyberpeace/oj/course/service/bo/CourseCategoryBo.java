package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CourseCategoryBo implements Serializable {

	/**
	 * 子信息
	 *
	 * @see List<CourseCategoryBo>
	 */
	@ApiModelProperty("子信息")
	private List<CourseCategoryBo> children;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	@JsonIgnore
	private String parentId;

	/**
	 * 资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 分类名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类名称")
	private String name;

	/**
	 * 课程类型 0=基础 2=攻击 3=防御
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
	private Integer courseType;

}
