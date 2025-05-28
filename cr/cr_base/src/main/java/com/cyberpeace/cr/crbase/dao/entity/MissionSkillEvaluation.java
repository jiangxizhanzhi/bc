/**
 * 该代码定义了一个名为 `MissionSkillEvaluation` 的实体类，用于表示任务技能评估的相关信息。以下是代码的简要分析： 
 * 1. **包与导入**： 
 * - 类位于 `com.cyberpeace.cr.crbase.dao.entity` 包中。 
 * - 使用了 `java.util.Date` 和 MyBatis-Plus 的注解（如 `@TableName`, `@TableId`）。 
 * 2. **注解说明**： 
 * - `@TableName("cr_mission_skillevaluation")`：指定数据库表名为 `cr_mission_skillevaluation`。 
 * - `@TableId(type = IdType.UUID, value = "resource_id")`：定义 `resource_id` 字段为 UUID 类型的主键。 
 * 3. **字段说明**： 
 * - `id`: 主键，类型为 `Integer`。 
 * - `resource_id`: 资源 ID，类型为 `String`，作为主键使用。 
 * - `studentId`: 学员 ID，类型为 `String`。 
 * - `roomId`: 房间 ID，类型为 `String`。 
 * - `skill`: 技能名称，类型为 `String`。 
 * - `score`: 得分，类型为 `Integer`。 
 * - `createTime`: 创建时间，类型为 `Date`。 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成 getter 和 setter 方法。 
 * 5. **用途**： 
 * - 该类主要用于数据持久化，与数据库表 `cr_mission_skillevaluation` 对应，便于操作任务技能评估的数据。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于映射数据库表 `cr_mission_skillevaluation`，并提供了自动化的 getter 和 setter 方法。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务技能评估
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission_skillevaluation")
public class MissionSkillEvaluation {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 学员id
	 */
	private String studentId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 技能
	 */
	private String skill;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 创建时间
	 */
	private Date createTime;

}
