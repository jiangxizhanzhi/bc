package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂监控请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ClassroomMonitorReq extends PageReqBase {

	/**
	 * 课堂表编号
	 */
	@ApiModelProperty("课堂表编号")
	private String classroomId;

	/**
	 * 模糊查询筛选参数(不会转义)
	 */
	@ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
	private String pattern;

	public void setClassroom_id(String classroomId) {
		this.classroomId = classroomId;
	}

}
