package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课堂群请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomGroupReq implements Serializable {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 群计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("每组人数")
	@NotNull
	private Integer groupCount;

}
