package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课堂群请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ClassroomGroupReq implements Serializable {

	/**
	 * 课堂id
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 群计数
	 */
	@ApiModelProperty("每组人数")
	@NotNull
	private Integer groupCount;

}
