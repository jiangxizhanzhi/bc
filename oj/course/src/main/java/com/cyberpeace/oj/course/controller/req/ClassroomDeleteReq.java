package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂删除请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomDeleteReq implements Serializable {

	/**
	 * 课堂编号
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String resourceId;

}
