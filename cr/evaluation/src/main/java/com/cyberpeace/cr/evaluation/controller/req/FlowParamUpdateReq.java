package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量参数更新请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FlowParamUpdateReq implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	/**
	 * 流量id
	 */
	@ApiModelProperty(value = "流量id")
	@NotNull
	private String flowId;

	/**
	 * 源ip地址
	 */
	@ApiModelProperty(value = "源ip")
	private String sourceIp;
}
