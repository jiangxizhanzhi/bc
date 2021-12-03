package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseCategoryBo implements Serializable {

	@ApiModelProperty("子信息")
	private List<CourseCategoryBo> children;

	@JsonIgnore
	private String parentId;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("分类名称")
	private String name;

	@ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
	private Integer courseType;

}
