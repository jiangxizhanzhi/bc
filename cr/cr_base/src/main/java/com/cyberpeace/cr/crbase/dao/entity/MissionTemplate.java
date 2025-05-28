/**
 * 这段代码定义了一个名为 `MissionTemplate` 的 Java 类，用于表示任务模板实体。以下是简要分析： 
 * 1. **类名与功能**： 
 * - 类名为 `MissionTemplate`，继承自 `MissionBase`。 
 * - 注释说明该类是用于任务模板的实体类。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，将该类映射到数据库表 `cr_mission_template`。 
 * 3. **属性**： 
 * - `templateId`：模板ID，类型为字符串。 
 * - `attachment`：附件，类型为字符串。 
 * - `businessTemplateId`：业务模板ID，类型为字符串。 
 * 4. **继承**： 
 * - 继承自 `MissionBase`，可能包含一些通用的任务基础属性或方法。 
 * 5. **用途**： 
 * - 该类主要用于数据库操作，结合 MyBatis-Plus 进行增删改查等操作。 
 * 总结：这是一个基于 MyBatis-Plus 的任务模板实体类，用于数据库表 `cr_mission_template` 的映射，支持基本的 CRUD 操作。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	/**
	 * 模板id
	 */
	private String templateId;

	/**
	 * 附件
	 */
	private String attachment;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;
}
