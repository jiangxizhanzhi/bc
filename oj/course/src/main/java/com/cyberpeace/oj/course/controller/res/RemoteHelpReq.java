package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * 远程帮助请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `RemoteHelpReq` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示远程帮助请求的数据结构。以下是代码的详细分析： 
 * ### 类和字段 
 * - **类名**：`RemoteHelpReq` 
 * - **实现接口**：`Serializable` 
 * - **字段**： 
 * - `lesson_id`：课程ID，必须非空（使用了 `@NotBlank` 注解）。 
 * - `node_id`：节点ID，必须非空（使用了 `@NotBlank` 注解）。 
 * - `room_id`：房间ID，必须非空（使用了 `@NotBlank` 注解）。 
 * ### 方法 
 * - **setter 方法**： 
 * - `setLesson_id(String lessonId)`：设置课程ID。 
 * - `setNode_id(String nodeId)`：设置节点ID。 
 * - `setRoom_id(String roomId)`：设置房间ID。 
 * - **getter 方法**： 
 * - `getLessonId()`：获取课程ID，并使用 `@ApiModelProperty` 注解描述其用途。 
 * - `getNodeId()`：获取节点ID，并使用 `@ApiModelProperty` 注解描述其用途。 
 * - `getRoomId()`：获取房间ID，并使用 `@ApiModelProperty` 注解描述其用途。 
 * ### 注解 
 * - **`@ApiModelProperty`**：用于描述API中的字段信息，包括名称和值。 
 * - **`@NotBlank`**：用于确保字段不为空或空白字符串。 
 * ### 总结 
 * `RemoteHelpReq` 类主要用于在远程帮助场景中传输必要的数据，如课程ID、节点ID和房间ID。通过实现 `Serializable` 接口，该类可以被序列化，便于在网络上传输。所有字段都是必需的，并且通过注解来确保这些字段的有效性。 
**/
public class RemoteHelpReq implements Serializable {

	/**
	 * 课id
	 *
	 * @see String
	 */
	@NotBlank
	private String lesson_id;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@NotBlank
	private String node_id;

	/**
	 * 房间id
	 *
	 * @see String
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

	@ApiModelProperty(name = "lesson_id", value = "课时编号")
	public String getLessonId() {
		return lesson_id;
	}

	@ApiModelProperty(name = "node_id", value = "虚拟机节点编号")
	public String getNodeId() {
		return node_id;
	}

	@ApiModelProperty(name = "room_id", value = "课程表编号")
	public String getRoomId() {
		return room_id;
	}
}

