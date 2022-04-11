package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class RemoteHelpReq implements Serializable {

	@ApiModelProperty(name = "lesson_id", value = "课时编号")
	@NotBlank
	private String lesson_id;

	@ApiModelProperty(name = "node_id", value = "虚拟机节点编号")
	@NotBlank
	private String node_id;

	public void setLesson_id(String lessonId) {
		this.lesson_id = lessonId;
	}

	public void setNode_id(String nodeId) {
		this.node_id = nodeId;
	}

	public String getLessonId() {
		return lesson_id;
	}

	public String getNodeId() {
		return node_id;
	}
}
