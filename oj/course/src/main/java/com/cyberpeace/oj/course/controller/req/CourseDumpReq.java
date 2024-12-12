package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程转 储请求体
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
public class CourseDumpReq implements Serializable {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("课程编号")
	private List<String> resourceIds;

}
