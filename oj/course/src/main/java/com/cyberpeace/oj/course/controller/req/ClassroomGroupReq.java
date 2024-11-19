package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课堂组请求体
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomGroupReq implements Serializable {

	/**
     * 课堂编号
     *
     * @see String
     */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
     * 每组人数
     *
     * @see Integer
     */
	@ApiModelProperty("每组人数")
	@NotNull
	private Integer groupCount;

}
