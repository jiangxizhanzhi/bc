package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * 远程帮助请求体
 *
 * @author SN
 * @date 2024/11/14
 */
public class RemoteHelpReq implements Serializable {

	/**
	 * 课时id
	 */
	@NotBlank
	private String lesson_id;

	/**
	 * 节点id
	 */
	@NotBlank
	private String node_id;

	/**
	 * 房间id
	 */
	@NotBlank
	private String room_id;

	public void setLesson_id(String lessonId) {
		this.lesson_id = lessonId;
	}

	public void setNode_id(String nodeId) {
		this.node_id = nodeId;
	}

	public void setRoom_id(String roomId) { this.room_id = roomId; }

	/**
	 * 课时编号
	 */
	@ApiModelProperty(name = "lesson_id", value = "课时编号")
	public String getLessonId() {
		return lesson_id;
	}

	/**
	 * 虚拟机节点编号
	 */
	@ApiModelProperty(name = "node_id", value = "虚拟机节点编号")
	public String getNodeId() {
		return node_id;
	}

	/**
	 * 课程表编号
	 */
	@ApiModelProperty(name = "room_id", value = "课程表编号")
	public String getRoomId() {
		return room_id;
	}
}
