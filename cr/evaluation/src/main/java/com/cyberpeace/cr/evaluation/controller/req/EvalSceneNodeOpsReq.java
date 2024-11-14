package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估场景节点操作请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalSceneNodeOpsReq implements Serializable {

	/**
	 * 测评靶场任务id
	 */
	@ApiModelProperty(value = "测评靶场任务id", required = true)
	@NotBlank
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
