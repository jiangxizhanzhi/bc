package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景流上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneFlowContext implements Serializable {

	@ApiModelProperty("流量Id")
	private String flowId;

	@ApiModelProperty("交互类型：0 背景流量，1 攻击流量，2 全流量")
	private Integer interactiveType;

	@ApiModelProperty("包含该流量的节点列表")
	private List<String> nodeIds = Lists.newArrayList();

	public SceneFlowContext(String flowId, Integer interactiveType) {
		this.flowId = flowId;
		this.interactiveType = interactiveType;
	}

	public SceneFlowContext() {}

}
