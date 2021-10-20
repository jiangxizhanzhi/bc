package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.AbstractBaseTree;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseCategoryBo extends AbstractBaseTree {

	@ApiModelProperty("分类名称")
	private String name;

	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
	private Integer courseType;

}
