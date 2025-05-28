package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号知识点
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
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
	private IndexTraffic traffic = new IndexTraffic();

	@ApiModelProperty(value = "算法库")
	private IndexAlgorithm algorithm;

	@ApiModelProperty(value = "场景库")
	private IndexScene scene = new IndexScene();

	@ApiModelProperty(value = "防御知识库总数")
	private Integer sum;

	public void calSum() {
		this.sum = this.theory.getSum() + this.strategy.getSum() + this.tactic.getSum()
				+ this.rule.getSum() + this.traffic.getSum() + this.algorithm.getSum()
				+ this.scene.getSum();
	}
}
