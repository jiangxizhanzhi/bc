package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TaskContext` 的类，它继承自 `BaseTable` 类，并且使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来简化属性的 getter 和 setter 方法生成。以下是对该代码的主要分析： 
 * ### 主要功能 
 * - **任务上下文类**：`TaskContext` 是一个用于表示任务上下文的数据传输对象（DTO），包含了与任务相关的各种信息。 
 * - **继承关系**：它继承自 `BaseTable`，这可能是一个通用的基础类，通常包含一些通用字段或方法。 
 * ### 关键特性 
 * 1. **字段说明** 
 * - `resourceId`: 唯一标识资源的 ID。 
 * - `score`: 表示任务得分。 
 * - `missionId`: 对应的任务 ID。 
 * - `status`: 当前任务的状态。 
 * - `userId`: 执行任务的用户 ID。 
 * - `userAnswer`: 用户提供的答案。 
 * - `releaseTime`: 任务发布时间。 
 * - `lastSubmitTime`: 最后一次提交的时间。 
 * - `taskStartTime`: 任务开始时间。 
 * - `roomId`: 房间 ID。 
 * - `roleId`: 岗位 ID。 
 * - `businessType`: 业务类型。 
 * - `mission`: 任务对象本身。 
 * - `remark`: 备注信息。 
 * 2. **状态检查** 
 * - 提供了一个 `isFinished()` 方法，用于判断当前任务是否已完成。通过检查 `status` 是否在 `CrTask.TaskStatus.FINISHED_STATUS_LIST` 中实现。 
 * 3. **注解使用** 
 * - 使用了 `@TableId` 注解来指定 `resourceId` 字段为主键，并设置了其生成策略为 UUID。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，减少了手动编写 getter 和 setter 方法的工作量。 
 * ### 总结 
 * 此代码片段定义了一个任务上下文类，主要用于存储和管理任务相关信息。通过继承 `BaseTable` 类和使用 Lombok 注解，提高了代码的可读性和开发效率。此外，还提供了一个便捷的方法来判断任务是否完成，增强了类的功能性。 
**/
@Getter
@Setter
public class TaskContext extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务id
	 */
	private String missionId;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户答案
	 */
	private String userAnswer;

	/**
	 * 发布时间
	 */
	private Date releaseTime;

	/**
	 * 上次提交时间
	 */
	private Date lastSubmitTime;

	/**
	 * 任务开始时间
	 */
	private Date taskStartTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 岗位id
	 */
	private String roleId;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 任务
	 */
	private Mission mission;

	/**
	 * 备注
	 */
	private String remark;

	public boolean isFinished() {
		return CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(status);
	}

}

