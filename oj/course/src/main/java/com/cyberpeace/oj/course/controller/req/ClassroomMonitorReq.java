package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂监控请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomMonitorReq extends PageReqBase {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂表编号")
	private String classroomId;

	/**
	 * 模式
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
	private String pattern;

	public void setClassroom_id(String classroomId) {
		this.classroomId = classroomId;
	}

}
