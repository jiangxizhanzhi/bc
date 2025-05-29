package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.algorithm.SpaceTacticDefenseSystemBo;
import lombok.Data;

import java.util.Map;

/**
 * 空间战法防御系统
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTacticDefenseSystem` 的实体类，用于表示空间战法防御系统的数据模型。以下是代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceTacticDefenseSystem` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 继承自 `SpaceBaseTable`，表明它是一个基于基础表的数据实体。 
 * 2. **注解说明**： 
 * - `@Data`：Lombok 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@TableName("space_tactic_defense_system")`：MyBatis-Plus 注解，指定该实体对应的数据库表名为 `space_tactic_defense_system`。 
 * 3. **字段说明**： 
 * - `stage`：整数类型，表示阶段。 
 * - `definition`：字符串类型，表示定义。 
 * 4. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为 `SpaceTacticDefenseSystemBo` 对象。使用了 `BeanUtils.copy` 方法进行对象属性的复制。 
 * 5. **依赖的其他类**： 
 * - `SpaceBaseTable`：基础表类，提供通用的字段和方法。 
 * - `IdAndName`：一个简单的数据传输对象（DTO）。 
 * - `SpaceTacticDefenseSystemBo`：业务对象，用于业务逻辑层。 
 * - `BeanUtils`：工具类，用于对象之间的属性复制。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于数据库操作，同时提供了将实体转换为业务对象的方法。 
**/
@Data
@TableName("space_tactic_defense_system")
public class SpaceTacticDefenseSystem extends SpaceBaseTable {

	private Integer stage;

	private String definition;

	public SpaceTacticDefenseSystemBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceTacticDefenseSystemBo bo = BeanUtils.copy(this, SpaceTacticDefenseSystemBo.class);
		return bo;
	}
}

