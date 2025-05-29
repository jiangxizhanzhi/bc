package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场任务提交
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrTaskSubmit` 的实体类，用于表示靶场任务提交的相关信息。以下是对其的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`CrTaskSubmit` 
 * - 包名：`com.cyberpeace.cr.crbase.dao.entity` 
 * - 作者：SN 
 * - 创建日期：2024年11月8日 
 * 2. **继承与注解**： 
 * - 继承自 `BaseTable`，表明它是一个数据库表对应的实体类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，指定该类对应的数据表名为 `cr_task_submit`。 
 * 3. **主要字段**： 
 * - `resource_id`：资源ID，使用 MyBatis-Plus 的 `@TableId` 注解，类型为 UUID。 
 * - `roomId`：房间ID。 
 * - `taskId`：任务ID。 
 * - `answer`：答案。 
 * - `answerExtension`：答案的外延信息。 
 * - `evaluatorId`：计算器ID。 
 * - `evaluateTime`：评估时间。 
 * - `evaluateStatus`：评估状态。 
 * - `reason`：原因。 
 * - `detail`：详细信息。 
 * - `score`：得分。 
 * - `missionScore`：任务分数。 
 * 4. **枚举类**： 
 * - `EvaluateStatus`：定义了评估状态的枚举类，包括等待评估、成功、失败、驳回和未交卷等状态。 
 * - `EvaluateType`：定义了评估类型的枚举类，包括机器评估和人工评估。 
 * 5. **功能**： 
 * - 该类主要用于存储和管理靶场任务提交的信息，包括任务的提交情况、评估结果等。 
 * 总体而言，这是一个典型的实体类，用于在数据库中存储和操作靶场任务提交的相关数据。 
**/
@Getter
@Setter
@TableName("cr_task_submit")
public class CrTaskSubmit extends BaseTable {

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
	private String taskId;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 答案外延
	 */
	private String answerExtension;

	/**
	 * 计算器id
	 */
	private String evaluatorId;

	/**
	 * 评估时间
	 */
	private Date evaluateTime;

	/**
	 * 评估状态
	 */
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	private Integer reason;

	/**
	 * 详情
	 */
	private String detail;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务分数
	 */
	private Integer missionScore;

	public enum EvaluateStatus {

		WAIT(0, "待评估"), SUCCESS(1, "成功"), FAIL(2, "失败"), REJECT(3, "驳回"), TASK_NOT_SUBMITTED(4,
				"未交卷");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EvaluateStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum EvaluateType {

		MACHINE_EVALUATION(0, "机器评估"), MANUAL_EVALUATION(1, "人工评估");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		EvaluateType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}

