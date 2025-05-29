package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmTeamBo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;


/**
 * 空间算法团队
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmTeam` 的 Java 类，以下是对其功能和结构的简要分析： 
 * 1. **类的基本信息**： 
 * - 类位于包 `com.cyberpeace.space.dao.entity` 中。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成属性的 getter 和 setter 方法。 
 * - 被标记为 MyBatis-Plus 的表注解 `@TableName("space_algorithm_team")`，表明该类与数据库中的 `space_algorithm_team` 表相对应。 
 * 2. **继承关系**： 
 * - 该类继承自 `SpaceBaseTable`，可能是为了复用一些基础字段或方法。 
 * 3. **属性**： 
 * - `expertisePercent`：表示专业知识的百分比，类型为整数。 
 * - `rolePercent`：表示岗位的百分比，类型也为整数。 
 * 4. **方法**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为业务对象 `SpaceAlgorithmTeamBo`。通过调用 `BeanUtils.copy` 方法实现对象的拷贝。 
 * 5. **依赖**： 
 * - 使用了工具类 `BeanUtils` 进行对象之间的属性复制。 
 * - 引入了 `IdAndName` 和 `SpaceAlgorithmTeamBo` 模型类，以及 `SpaceBaseTable` 基础类。 
 * 总结：这是一个典型的持久层实体类，用于映射数据库表 `space_algorithm_team`，并提供了将实体对象转换为业务对象的功能。 
**/
@Getter
@Setter
@TableName("space_algorithm_team")
public class SpaceAlgorithmTeam extends SpaceBaseTable {

	/**
	 * 专业知识百分比
	 */
	private Integer expertisePercent;

	/**
	 * 岗位百分比
	 */
	private Integer rolePercent;

	public SpaceAlgorithmTeamBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAlgorithmTeamBo bo = BeanUtils.copy(this, SpaceAlgorithmTeamBo.class);
		return bo;
    }
}
    
