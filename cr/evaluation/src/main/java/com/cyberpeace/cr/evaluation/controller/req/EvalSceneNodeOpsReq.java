package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估场景节点操作 计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalSceneNodeOpsReq implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "测评靶场任务id", required = true)
	@NotBlank
	private String taskId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
