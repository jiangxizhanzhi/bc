package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HelpStatusBo implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("课时编号")
	private String lessonId;

	@ApiModelProperty("节点编号")
	private String nodeId;

	@ApiModelProperty("状态 0=为请求协助 1=请求协助 2=协助中 3=协助已结束")
	private Integer status;

}
