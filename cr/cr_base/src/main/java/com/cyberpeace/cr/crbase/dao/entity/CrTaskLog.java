/**
 * 这段代码定义了一个名为 `CrTaskLog` 的实体类，用于表示靶场任务日志。以下是对代码的简要分析： 
 * 1. **包与导入**： 
 * - 类位于 `com.cyberpeace.cr.crbase.dao.entity` 包中。 
 * - 使用了 MyBatis-Plus 提供的注解 (`@TableName`, `@TableId`) 和 Lombok 注解 (`@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor`, `@Builder`)。 
 * 2. **继承关系**： 
 * - `CrTaskLog` 继承自 `BaseTable`，表明它是一个数据库表对应的实体类，并可能包含一些通用的字段或方法。 
 * 3. **注解说明**： 
 * - `@TableName("cr_task_log")`：指定该类对应的数据库表名为 `cr_task_log`。 
 * - `@TableId(type = IdType.UUID, value = "resource_id")`：定义 `resource_id` 字段为主键，类型为 UUID。 
 * 4. **字段说明**： 
 * - `resourceId`：资源 ID，主键。 
 * - `roomId`：房间 ID。 
 * - `missionId` 和 `taskId`：任务相关的 ID。 
 * - `actionCode`：行动代号，枚举类型。 
 * - `userId`：用户 ID。 
 * 5. **枚举 `ActionCode`**： 
 * - 定义了一系列任务相关的操作码及其描述，如任务下发、开放题解、关闭题解等。 
 * 6. **Lombok 注解**： 
 * - 自动生成 getter、setter、构造函数、toString 等方法，简化代码。 
 * 总结：这是一个基于 MyBatis-Plus 的实体类，用于存储靶场任务日志数据，支持丰富的功能（如 UUID 主键、枚举类型等），并使用 Lombok 提高开发效率。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 靶场任务日志
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("cr_task_log")
public class CrTaskLog extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 行动代号
	 */
	private Integer actionCode;

	/**
	 * 用户id
	 */
	private String userId;

	public enum ActionCode {

		RELEASE(1, "下发任务"), OPEN_WRITEUP(2, "开放题解"), CLOSE_WRITEUP(3, "关闭题解"), SUBMIT(4,
				"上报任务"), ROBOT_SUCCESS(5, "机评成功"), ROBOT_FAILURE(6, "机评失败"), MAN_SUCCESS(7,
						"人评成功"), MAN_FAILURE(8, "人评失败"), MAN_REJECT(9, "人评驳回");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		ActionCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
