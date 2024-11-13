package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景流量上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SceneFlowContext implements Serializable {

	/**
	 * 流量id
	 */
	@ApiModelProperty("流量Id")
	private String flowId;

	/**
	 * 交互式类型
	 */
	@ApiModelProperty("交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

	/**
	 * 节点id列表
	 */
	@ApiModelProperty("包含该流量的节点列表")
	private List<String> nodeIds = Lists.newArrayList();

	public SceneFlowContext(String flowId, Integer interactiveType) {
		this.flowId = flowId;
		this.interactiveType = interactiveType;
	}

	public SceneFlowContext() {}

}
