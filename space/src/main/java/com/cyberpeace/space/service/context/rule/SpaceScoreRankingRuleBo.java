/**
 * ### 分析与总结 
 * #### 代码功能概述： 
 * 该代码定义了一个名为 `SpaceScoreRankingRuleBo` 的 Java 类，它是基于 `SpaceBoBase` 的扩展类。这个类主要用于表示“空间得分排名规则”的业务对象（BO），并提供了将该对象转换为实体对象的方法。 
 * --- 
 * #### 主要特性： 
 * 1. **继承与基础类**： 
 * - `SpaceScoreRankingRuleBo` 继承自 `SpaceBoBase`。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法，简化了代码冗余。 
 * 2. **字段定义**： 
 * - 定义了多个字段用于描述空间得分排名规则，包括资源 ID、名称、描述、任务分数类型、回合分数类型、总和分数类型以及不同类型的奖励分数（如第一滴血、第二滴血、第三滴血）。 
 * - 每个字段都通过 Swagger 注解 `@ApiModelProperty` 添加了注释，便于 API 文档生成。 
 * 3. **方法实现**： 
 * - 提供了 `bo2Entity()` 方法，用于将当前 BO 对象转换为 `SpaceScoreRankingRule` 实体对象。此方法借助了工具类 `BeanUtils.copy` 来完成对象的复制。 
 * 4. **依赖工具类**： 
 * - 使用了 `com.cyberpeace.common.util.BeanUtils` 和 `com.cyberpeace.common.util.JsonUtil` 工具类，分别用于对象复制和 JSON 数据处理。 
 * --- 
 * #### 适用场景： 
 * - 该类适用于需要定义和管理“空间得分排名规则”的业务场景。 
 * - 在系统中，它可能被用于前端数据传输、后端业务逻辑处理以及数据库实体映射。 
 * --- 
 * #### 优点： 
 * - **简洁性**：通过 Lombok 减少了样板代码。 
 * - **可读性**：Swagger 注解增强了字段的语义化描述。 
 * - **复用性**：`bo2Entity()` 方法实现了 BO 到实体的转换，提升了代码复用性。 
 * --- 
 * #### 可能的改进建议： 
 * 1. **异常处理**：`bo2Entity()` 方法未处理潜在的异常，例如 `BeanUtils.copy` 抛出的异常。 
 * 2. **字段验证**：可以引入校验注解（如 `javax.validation.constraints`），确保字段值符合预期。 
 * 3. **泛型支持**：如果 `BeanUtils.copy` 支持泛型，可以进一步增强代码的灵活性。 
 * --- 
 * #### 总结： 
 * `SpaceScoreRankingRuleBo` 是一个典型的业务对象类，设计合理且功能明确。通过继承和工具类的支持，它能够高效地完成 BO 到实体的转换，并具备良好的扩展性和维护性。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间得分排名统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceScoreRankingRuleBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "规则简述")
	private String description;
	/**
	 * 任务分数类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "单任务记分")
	private Integer taskScoreType;
	/**
	 * 回合分数类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "单轮次记分")
	private Integer roundScoreType;
	/**
	 * 总和分数类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "累计记分")
	private Integer sumScoreType;
	/**
	 * 第一滴血
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "一血奖励")
	private Integer firstBlood;
	/**
	 * 第二滴血
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "二血奖励")
	private Integer secondBlood;
	/**
	 * 第三滴血
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "三血奖励")
	private Integer thirdBlood;

	/**
	 * BO2 实体
	 *
	 * @return SpaceScore排名规则
	 * @throws
	 * @see SpaceScoreRankingRule
	 */
	public SpaceScoreRankingRule bo2Entity() {
		SpaceScoreRankingRule entity = BeanUtils.copy(this, SpaceScoreRankingRule.class);
		return entity;
	}

}
    