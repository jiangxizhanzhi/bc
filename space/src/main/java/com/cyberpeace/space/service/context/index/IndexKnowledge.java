package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndexKnowledge {

	@ApiModelProperty(value = "理论知识库")
	private IndexTheory theory;

	@ApiModelProperty(value = "策略库")
	private IndexStrategy strategy;

	@ApiModelProperty(value = "战法库")
	private IndexTactic tactic;

	@ApiModelProperty(value = "规则库")
	private IndexRule rule;

	@ApiModelProperty(value = "流量库")
	private IndexTraffic traffic;

	@ApiModelProperty(value = "算法库")
	private IndexAlgorithm algorithm;

	@ApiModelProperty(value = "场景库")
	private IndexScene scene;
}
