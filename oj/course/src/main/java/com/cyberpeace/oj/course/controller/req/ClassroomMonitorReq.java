package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ClassroomMonitorReq extends PageReqBase {

	@ApiModelProperty("课堂表编号")
	private String classroomId;

	@ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
	private String pattern;

	@ApiModelProperty(value = "批阅状态")
	private Integer status;

	public void setClassroom_id(String classroomId) {
		this.classroomId = classroomId;
	}

}
