/**
 * ### 分析与总结 
 * #### 1. **代码功能** 
 * 该代码定义了一个名为 `SpaceRoundMechanismRule` 的实体类，用于表示“Space Round”机构规则的相关信息。它继承自 `SpaceBaseTable` 类，并使用了 MyBatis-Plus 的注解 `@TableName` 来映射数据库表 `space_round_mechanism_rule`。 
 * #### 2. **主要字段** 
 * - `description`: 描述信息。 
 * - `roundLength`: 每轮的时长（单位：秒）。 
 * - `initScore`: 初始分值。 
 * - `maxResetTime`: 最大重置次数。 
 * - `minResetInterval`: 最小重置间隔（单位：秒）。 
 * 这些字段通过 `@ApiModelProperty` 注解描述了其含义，便于生成 API 文档。 
 * #### 3. **继承关系** 
 * - 继承自 `SpaceBaseTable`，表明该类可能包含一些通用的字段或方法，例如主键、创建时间、更新时间等。 
 * #### 4. **方法** 
 * - `entity2Bo`: 将当前实体对象转换为 `SpaceRoundMechanismRuleBo` 对象。使用了工具类 `BeanUtils.copy` 进行对象的属性复制，同时需要传入一个 `relatedMap` 参数（可能是为了处理关联数据）。 
 * #### 5. **设计特点** 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，简化了 getter 和 setter 方法的生成。 
 * - 借助 Swagger 的 `@ApiModelProperty` 注解，提升了代码的可读性和文档生成能力。 
 * - 数据库表映射通过 `@TableName` 注解实现，方便 MyBatis-Plus 进行操作。 
 * #### 6. **适用场景** 
 * 该类适用于需要管理“Space Round”机制规则的业务场景，例如在线游戏、比赛系统或其他需要动态配置规则的系统。 
 * #### 总结 
 * 此代码是一个典型的实体类，主要用于数据持久化和业务逻辑中对象的传递。它结构清晰，注解丰富，体现了良好的代码规范和设计思路。 
**/
package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceRoundMechanismRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Round 机构规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_round_mechanism_rule")
public class SpaceRoundMechanismRule extends SpaceBaseTable {

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "每轮时长")
	private Integer roundLength;

	@ApiModelProperty(value = "初始分值")
	private Integer initScore;

	@ApiModelProperty(value = "重置最大次数")
	private Integer maxResetTime;

	@ApiModelProperty(value = "重置最小间隔")
	private Integer minResetInterval;


	public SpaceRoundMechanismRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceRoundMechanismRuleBo bo = BeanUtils.copy(this, SpaceRoundMechanismRuleBo.class);
		return bo;
	}
}
