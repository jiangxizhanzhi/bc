/**
 * 这段代码定义了一个名为 `MissionSchemeTemplate` 的实体类，用于表示任务计划模板。以下是代码的简要分析： 
 * 1. **包与导入**： 
 * - 类位于 `com.cyberpeace.cr.crbase.dao.entity` 包中。 
 * - 使用了 MyBatis-Plus 提供的注解（如 `@TableName` 和 `@TableId`）来映射数据库表和字段。 
 * - 引入了 `BaseTable` 类作为父类，并使用 Lombok 提供的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * 2. **注解与元数据**： 
 * - `@TableName("cr_mission_scheme_template")` 指定该类对应的数据库表名为 `cr_mission_scheme_template`。 
 * - `@TableId(type = IdType.UUID, value = "resource_id")` 定义了主键字段 `resource_id`，其类型为 UUID。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，作为主键。 
 * - `createUserId`：创建用户的 ID。 
 * - `title`：任务计划模板的标题。 
 * - `description`：任务计划模板的描述。 
 * - `templateData` 和 `templateConfig`：分别存储模板的数据和配置信息，附带了注释链接以提供更多背景信息。 
 * 4. **继承与设计**： 
 * - 继承自 `BaseTable`，可能包含一些通用的字段或方法（如创建时间、更新时间等）。 
 * 5. **功能**： 
 * - 该类主要用于 ORM（对象关系映射），便于在数据库中操作任务计划模板相关的数据。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于数据库表 `cr_mission_scheme_template` 的映射，提供了基本的 CRUD 功能支持。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务计划模板
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission_scheme_template")
public class MissionSchemeTemplate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 说明：http://10.10.10.11/pages/viewpage.action?pageId=34113495
	 */
	private String templateData;

	/**
	 * 说明： http://10.10.10.11/pages/viewpage.action?pageId=38209993
	 */
	private String templateConfig;

}
