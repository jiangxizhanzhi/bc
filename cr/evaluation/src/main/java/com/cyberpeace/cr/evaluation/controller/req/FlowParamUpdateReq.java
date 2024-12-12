package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 流参数更新请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
	 * 流id
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
