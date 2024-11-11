package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂标志在请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class ClassroomSignInReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String resourceId;

}
