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

/**
 * 这段代码定义了一个名为 `EvalTask` 的实体类，用于表示评估任务。以下是对代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **继承关系**：该类继承自 `BaseTable`，表明它是一个数据库表对应的实体类。 
 * ### 注解 
 * - `@TableName("cr_eval_task")`：指定该类映射到数据库中的 `cr_eval_task` 表。 
 * - `@Getter` 和 `@Setter`：使用 Lombok 自动生成 getter 和 setter 方法。 
 * ### 属性 
 * - `resourceId`：任务的唯一标识符（类型为 UUID）。 
 * - `templateId`：模板 ID。 
 * - `templateName`：模板名称。 
 * - `taskName`：任务名称。 
 * - `localSceneId`：本地场景 ID。 
 * - `mpSceneId`：多点场景 ID。 
 * - `copyedTopoId`：复制的拓扑 ID。 
 * - `startTime`：任务开始时间。 
 * - `endTime`：任务结束时间。 
 * - `enableRecording`：是否启用录制。 
 * - `createUserId`：创建用户 ID。 
 * ### 枚举 
 * - `Status`：定义了任务的状态枚举。 
 * - `RUNNING`：状态码为 1，描述为“运行中”。 
 * - `FINISHED`：状态码为 2，描述为“已结束”。 
 * ### 继承的基类 `BaseTable` 
 * - `BaseTable` 可能包含一些通用的字段或方法，例如创建时间、更新时间等。 
 * ### 总结 
 * 这个类主要用于表示一个评估任务，并且通过继承 `BaseTable` 类来实现与数据库的交互。枚举 `Status` 定义了任务的不同状态，这些状态可以通过 `code` 和 `desc` 进行访问。整体来看，这是一个典型的 Java 实体类，用于数据持久化和业务逻辑处理。 
**/
@Getter
@Setter
@TableName("cr_eval_task")
public class EvalTask extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String templateName;

	private String taskName;

	private String localSceneId;

	private String mpSceneId;

	private String copyedTopoId;

	private Date startTime;

	private Date endTime;

	private Boolean enableRecording;

	private String createUserId;

	public enum Status implements GenericEnum {

		RUNNING(1, "运行中"), FINISHED(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}

