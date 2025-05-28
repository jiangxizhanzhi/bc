/**
 * ### 代码分析 
 * #### 1. **类的功能** 
 * - `CrTask` 是一个实体类，用于表示靶场任务的相关信息。 
 * - 它继承了 `BaseTable` 类，通常用于数据库表映射。 
 * #### 2. **主要字段** 
 * - `resourceId`: 任务资源的唯一标识符，使用 UUID。 
 * - `status`: 任务的状态（枚举值）。 
 * - `score`: 任务得分。 
 * - `missionId`: 任务 ID。 
 * - `userId`: 用户 ID。 
 * - `userAnswer`: 用户的答案。 
 * - `releaseTime`: 任务发布时间。 
 * - `lastSubmitTime`: 最后一次提交时间。 
 * - `taskStartTime`: 任务开始时间。 
 * - `roomId`: 房间 ID。 
 * - `roleId`: 岗位 ID。 
 * - `businessType`: 业务类型。 
 * - `missionTemplateId`: 任务模板 ID。 
 * - `remark`: 备注信息。 
 * #### 3. **方法** 
 * - `isFinished()`: 判断任务是否完成。如果状态为 `SUCCESS` 或 `FAIL`，则返回 `true`。 
 * - `isSubmitted()`: 判断任务是否已提交。如果状态为 `RELEASED` 或 `STARTED`，则返回 `false`。 
 * - `isSuccess()`: 判断任务是否成功。如果状态为 `SUCCESS`，则返回 `true`。 
 * #### 4. **枚举类** 
 * - `SingleTaskStatus`: 表示单个任务的状态，实现了 `GenericEnum` 和 `BaseI18n` 接口，支持多语言描述。 
 * - `TaskStatus`: 表示任务的整体状态，包含多个状态值及其描述，并提供了静态列表来分类状态（如已完成、未提交、未完成等）。 
 * #### 5. **技术特点** 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 和 `@TableId` 注解，用于数据库表映射。 
 * - 枚举类的设计符合通用的枚举模式，便于扩展和维护。 
 * #### 6. **总结** 
 * - 该类结构清晰，功能明确，适合用于管理靶场任务的生命周期。 
 * - 枚举类的设计提高了代码的可读性和可维护性。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.constant.BaseI18n;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场任务
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_task")
public class CrTask extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务id
	 */
	private String missionId;

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
	 * 任务模板id
	 */
	private String missionTemplateId;

	/**
	 * 备注
	 */
	private String remark;

	public Boolean isFinished() {
		boolean result = status.intValue() == TaskStatus.SUCCESS.getCode();
		result = result || status.intValue() == TaskStatus.FAIL.getCode();

		return result;
	}

	public Boolean isSubmitted() {
		boolean result = status.intValue() == TaskStatus.RELEASED.getCode();
		result = result || status.intValue() == TaskStatus.STARTED.getCode();
		return !result;
	}

	public Boolean isSuccess() {
		return TaskStatus.SUCCESS.getCode().equals(status) ;
	}
	/**
	 * task 单兵状态枚举
	 */
	public enum SingleTaskStatus implements GenericEnum, BaseI18n {

		/***/
		UNRELEASED(10, "未下发", "Unassigned"), RELEASED(11, "未提交", "Unsubmitted"), SUCCESS(1, "成功",
				"Successful"), FAIL(2, "失败", "Failed");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		SingleTaskStatus(Integer code, String desc, String enDesc) {
			this.code = code;
			this.chinese = desc;
			this.english = enDesc;
		}

		@Override
		public String getDesc() {
			return getMsg();
		}

	}

	/**
	 * task 状态枚举
	 */
	public enum TaskStatus implements GenericEnum{

		UNRELEASED(10, "未下发"), RELEASED(11, "已下发"), STARTED(12, "已开始"), WAIT(0, "待评估"), SUCCESS(1,
				"成功"), FAIL(2, "失败"), REJECT(3, "驳回");

		public static List<Integer> FINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(SUCCESS.getCode(), FAIL.getCode()));

		public static List<Integer> UNSUBMITTED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(RELEASED.getCode(), STARTED.getCode()));

		public static List<Integer> UNFINISHED_STATUS_LIST = new ArrayList<>(
				Arrays.asList(WAIT.getCode(), REJECT.getCode()));

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TaskStatus(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
