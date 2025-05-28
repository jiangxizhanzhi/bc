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

	/**
	 * 理论
	 *
	 * @see IndexTheory
	 */
	@ApiModelProperty(value = "理论知识库")
	private IndexTheory theory;

	/**
	 * 策略
	 *
	 * @see IndexStrategy
	 */
	@ApiModelProperty(value = "策略库")
	private IndexStrategy strategy;

	/**
	 * 战法
	 *
	 * @see IndexTactic
	 */
	@ApiModelProperty(value = "战法库")
	private IndexTactic tactic;

	/**
	 * 统治
	 *
	 * @see IndexRule
	 */
	@ApiModelProperty(value = "规则库")
	private IndexRule rule;

	/**
	 * 流量
	 *
	 * @see IndexTraffic
	 */
	@ApiModelProperty(value = "流量库")
	private IndexTraffic traffic = new IndexTraffic();

	/**
	 * 算法
	 *
	 * @see IndexAlgorithm
	 */
	@ApiModelProperty(value = "算法库")
	private IndexAlgorithm algorithm;

	/**
	 * 场景
	 *
	 * @see IndexScene
	 */
	@ApiModelProperty(value = "场景库")
	private IndexScene scene = new IndexScene();

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防御知识库总数")
	private Integer sum;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.theory.getSum() + this.strategy.getSum() + this.tactic.getSum()
				+ this.rule.getSum() + this.traffic.getSum() + this.algorithm.getSum()
				+ this.scene.getSum();
	}
}
