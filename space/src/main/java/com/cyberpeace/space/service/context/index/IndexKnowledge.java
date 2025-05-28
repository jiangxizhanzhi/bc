/**
 * 该代码定义了一个名为 `IndexKnowledge` 的 Java 类，用于表示编号知识点（index knowledge）。以下是对其功能和结构的简要分析： 
 * ### 主要功能： 
 * - **数据存储**：类中包含多个属性，每个属性都代表不同类型的知识库（如理论、策略、战法等），并使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - **计算总和**：提供了一个 `calSum()` 方法，用于计算所有子知识库的总和，并将结果存储在 `sum` 属性中。 
 * ### 属性说明： 
 * 1. **理论 (`theory`)**：指向一个 `IndexTheory` 对象，表示理论知识库。 
 * 2. **策略 (`strategy`)**：指向一个 `IndexStrategy` 对象，表示策略库。 
 * 3. **战法 (`tactic`)**：指向一个 `IndexTactic` 对象，表示战法库。 
 * 4. **规则 (`rule`)**：指向一个 `IndexRule` 对象，表示规则库。 
 * 5. **流量 (`traffic`)**：指向一个 `IndexTraffic` 对象，默认值为新创建的对象，表示流量库。 
 * 6. **算法 (`algorithm`)**：指向一个 `IndexAlgorithm` 对象，表示算法库。 
 * 7. **场景 (`scene`)**：指向一个 `IndexScene` 对象，默认值为新创建的对象，表示场景库。 
 * 8. **总和 (`sum`)**：整数类型，表示所有子知识库的总和。 
 * ### 方法说明： 
 * - **`calSum()`**：计算所有子知识库的 `sum` 属性值，并将其赋值给当前对象的 `sum` 属性。此方法依赖于每个子知识库都实现了 `getSum()` 方法。 
 * ### 总结： 
 * 该类是一个知识管理工具的核心部分，负责整合多种类型的知识库，并提供一个接口来计算这些知识库的总和。通过默认初始化某些属性（如 `traffic` 和 `scene`），它简化了对象的创建过程，同时提供了灵活的数据管理和计算能力。 
**/
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
