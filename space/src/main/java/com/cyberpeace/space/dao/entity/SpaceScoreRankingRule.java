/**
 * 这段代码定义了一个名为 `SpaceScoreRankingRule` 的 Java 类，用于表示空间评分排名规则。以下是对其的简要分析： 
 * 1. **类的继承与注解**： 
 * - `SpaceScoreRankingRule` 继承自 `SpaceBaseTable`，表明它是一个基于数据库表的实体类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@TableName("space_score_ranking_rule")` 注解指定了该类对应的数据库表名为 `space_score_ranking_rule`。 
 * 2. **字段说明**： 
 * - 包含多个字段（如 `description`, `taskScoreType`, `roundScoreType` 等），每个字段都有 Swagger 文档注解 `@ApiModelProperty` 来描述其用途。 
 * - 这些字段主要用于存储不同类型的评分规则信息。 
 * 3. **方法功能**： 
 * - 提供了一个 `entity2Bo` 方法，将当前对象转换为 `SpaceScoreRankingRuleBo` 对象。此方法利用了 `BeanUtils.copy` 方法实现属性拷贝。 
 * 4. **依赖关系**： 
 * - 依赖于外部工具类 `BeanUtils` 和其他模型类 `IdAndName`, `SpaceBaseTable`, `SpaceScoreRankingRuleBo`。 
 * 总体而言，这个类的设计目的是封装空间评分排名相关的业务逻辑，并提供数据传输对象（DTO）之间的转换能力。 
**/
package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceScoreRankingRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Score 排名规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_score_ranking_rule")
public class SpaceScoreRankingRule extends SpaceBaseTable {
    @ApiModelProperty(value = "规则简述")
	private String description;
    @ApiModelProperty(value = "单任务记分")
	private Integer taskScoreType;
    @ApiModelProperty(value = "单轮次记分")
	private Integer roundScoreType;
    @ApiModelProperty(value = "累计记分")
	private Integer sumScoreType;
    @ApiModelProperty(value = "一血奖励")
	private Integer firstBlood;
    @ApiModelProperty(value = "二血奖励")
	private Integer secondBlood;
    @ApiModelProperty(value = "三血奖励")
	private Integer thirdBlood;


	public SpaceScoreRankingRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceScoreRankingRuleBo bo = BeanUtils.copy(this, SpaceScoreRankingRuleBo.class);
		return bo;
	}
}
