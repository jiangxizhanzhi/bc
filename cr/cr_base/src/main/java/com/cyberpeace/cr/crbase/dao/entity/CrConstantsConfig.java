/**
 * 这段代码定义了一个名为 `CrConstantsConfig` 的 Java 实体类，用于表示靶场常数配置。以下是代码的主要分析： 
 * 1. **类与注解**： 
 * - 类 `CrConstantsConfig` 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，将该类映射到数据库表 `cr_constants_config`。 
 * 2. **字段**： 
 * - `id`：使用了 MyBatis-Plus 的 `@TableId` 注解，表示主键字段。 
 * - `resourceId`：资源 ID。 
 * - `code`：代号。 
 * - `type`：类型。 
 * - `cnName` 和 `enName`：分别表示中文名称和英文名称。 
 * 3. **接口实现**： 
 * - 类实现了 `SelectI18nName` 接口，可能用于国际化名称的选择。 
 * 4. **枚举类**： 
 * - 内部定义了一个 `ConstantsType` 枚举类，用于描述常量项的类别。每个枚举值包含一个 `code` 和 `desc`，分别表示代码和描述。 
 * 总结：这是一个典型的实体类，结合了 Lombok 和 MyBatis-Plus 的功能，用于数据库表的映射，并且通过枚举类提供了常量项的类别定义。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.constant.SelectI18nName;

import lombok.Data;
import lombok.Getter;

/**
 * 靶场常数配置
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("cr_constants_config")
public class CrConstantsConfig implements SelectI18nName {

	/**
	 * id
	 */
	@TableId
	private Integer id;

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 代号
	 */
	private Integer code;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 中文名称
	 */
	private String cnName;

	/**
	 * 中文名称
	 */
	private String enName;

	public enum ConstantsType {

		/* 常量项类别**/
		MISSION_CATEGORY(1, "任务分类"),
		DIFFICULTY(2, "训练难度"),
		TRAINING_CATEGORY(3, "训练分类"),
		EVALUATION_CATEGORY(4, "测试靶场分类"),
		ALGORITHM_TYPE(5, "算法分类"),
		COURSE_DIFFICULTY(9, "课程难度"),
		PRACTICE_DIFFICULTY(10, "练习难度"),
		PRACTICE_STATUS(11, "练习状态"),
		PRACTICE_SET_TYPE(12, "习题集类型"),
		COLLECTION_TYPE(13, "收藏类型"),

		CLASS_DIFFICULTY(15, "课时难度"),
		CLASS_TYPE(16, "课时类型"),
		STUDY_TYPE(17, "课时学习类型"),
		COMMENT_TYPE(18, "评论类型"),
		NOTE_TYPE(19, "笔记类型"),
		RESOURCE_STATUS(20, "资源类型"),
		QUESTION_TYPE(21, "题库题型类型"),
		SINGLE_DIFFICULTY(22, "单兵训练难度"),
		TEAM_DIFFICULTY(23, "团队训练难度"),
		RED_BLUE_DIFFICULTY(24, "红蓝对抗难度"),
		COMPETITION_STATUS(25, "赛场状态"),
		COMPETE_TYPE(26, "参赛方式"),
		MATCH_TYPE(27, "赛事类型"),
		MATCH_STATUS(28, "赛事状态"),
		MESSAGE_MODULE_TYPE(31, "留言主题类型"),
		STUDY_STATISTICS(37,"学习统计类型难度"),
		EVAL_STATUS(38, "靶场测评评估状态"),
		WEB_PRACTICE_STATUS(39, "三种练习状态"),
		Note_type(40, "笔记资源类型"),
		TOOL_TYPE(43, "工具类别"),
		ALGORITHM_APPLY_TYPE(56, "");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		ConstantsType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
