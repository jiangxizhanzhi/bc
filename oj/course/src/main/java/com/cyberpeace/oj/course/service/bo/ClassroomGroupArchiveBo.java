package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课堂组档案 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ClassroomGroupArchiveBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("模板编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("模板名称")
	private String name;

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂编号")
	private String classroomId;
}
