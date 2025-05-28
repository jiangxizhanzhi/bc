/**
 * 这段代码定义了一个名为 `EvalTaskStep` 的 Java 类，该类继承自 `BaseTable`。这个类代表一个评估任务步骤，并且使用了 MyBatis-Plus 框架来处理数据库操作。以下是对代码的简要分析： 
 * ### 包和导入 
 * - `com.cyberpeace.cr.evaluation.dao.entity`: 这个类位于 `com.cyberpeace.cr.evaluation.dao.entity` 包中。 
 * - 导入了多个类和接口，包括日期类、MyBatis-Plus 注解、枚举接口以及 Lombok 注解。 
 * ### 类定义 
 * - `@Getter` 和 `@Setter`: 由 Lombok 提供，用于自动生成属性的 getter 和 setter 方法。 
 * - `@TableName("cr_eval_task_step")`: 指定这个类映射到数据库中的 `cr_eval_task_step` 表。 
 * ### 属性 
 * - `resourceId`: 任务步骤的唯一标识符（UUID 类型）。 
 * - `taskId`: 关联的任务 ID。 
 * - `stage`: 任务阶段。 
 * - `title`: 步骤标题。 
 * - `process`: 处理过程。 
 * - `type`: 步骤类型。 
 * - `startTime`: 开始时间。 
 * - `endTime`: 结束时间。 
 * ### 枚举类型 
 * - `Type`: 内部枚举类，实现了 `GenericEnum` 接口。定义了两种类型：`MANUAL` 和 `AUTO`。 
 * - `code`: 类型编码。 
 * - `desc`: 类型描述。 
 * ### 总结 
 * - `EvalTaskStep` 类表示一个评估任务步骤，包含多个属性如步骤 ID、任务 ID、阶段、标题、处理过程、类型等。 
 * - 使用 MyBatis-Plus 的注解来映射数据库表。 
 * - 内部枚举类型 `Type` 定义了步骤的类型，并提供了类型编码和描述。 
 * - 使用 Lombok 来简化代码，减少样板代码。 
 * 这个类主要用于数据持久化和业务逻辑处理，是一个典型的实体类。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@TableName("cr_eval_task_step")
public class EvalTaskStep extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String taskId;

	private Integer stage;

	private String title;

	private String process;

	private Integer type;

	private Date startTime;

	private Date endTime;

	public enum Type implements GenericEnum {

		MANUAL(1, "手动"), AUTO(2, "自动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
