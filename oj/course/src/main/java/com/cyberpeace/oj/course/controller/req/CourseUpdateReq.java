package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 课程更新请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class CourseUpdateReq extends CourseAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课程资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}
