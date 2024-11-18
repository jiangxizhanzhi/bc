package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量参数更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowParamUpdateReq implements Serializable {

	/**
	 * 任务id
	 *
	 * @see String
	 */
	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	/**
	 * 流量id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "流量id")
	@NotNull
	private String flowId;

	/**
	 * 源ip地址
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "源ip")
	private String sourceIp;
}
