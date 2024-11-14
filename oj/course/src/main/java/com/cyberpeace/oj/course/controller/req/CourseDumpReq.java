package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程导出请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class CourseDumpReq implements Serializable {

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private List<String> resourceIds;

}
