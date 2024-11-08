package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 课实践场景节点请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class LessonPracticeSceneNodeReq extends LessonPracticeSceneReq {

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

}
