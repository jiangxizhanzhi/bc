package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class RemoteHelpReq implements Serializable {

	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

	@ApiModelProperty("虚拟机节点编号")
	@NotBlank
	private String nodeId;

	public void setLesson_id(String lessonId) {
		this.lessonId = lessonId;
	}

	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}
}
