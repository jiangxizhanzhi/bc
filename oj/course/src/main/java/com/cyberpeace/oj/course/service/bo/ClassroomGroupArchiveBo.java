package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课堂组档案Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomGroupArchiveBo implements Serializable {

	/**
	 * 模板编号
	 */
	@ApiModelProperty("模板编号")
	private String resourceId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	private String name;

	/**
	 * 课堂编号
	 */
	@ApiModelProperty("课堂编号")
	private String classroomId;
}
