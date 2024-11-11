package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程类别 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class CourseCategoryBo implements Serializable {

	/**
	 * 子级
	 */
	@ApiModelProperty("子信息")
	private List<CourseCategoryBo> children;

	/**
	 * 父母id
	 */
	@JsonIgnore
	private String parentId;

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("分类名称")
	private String name;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
	private Integer courseType;

}
