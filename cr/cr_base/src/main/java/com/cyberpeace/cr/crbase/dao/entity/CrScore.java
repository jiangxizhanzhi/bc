package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场得分
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `CrScore` 的实体类，用于表示靶场中的得分记录。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * - **类定义**：`CrScore` 是一个继承自 `BaseTable` 的实体类，用于存储靶场中用户的得分信息。 
 * - **数据库表映射**：通过 `@TableName` 注解指定对应的数据库表名为 `cr_score`。 
 * - **字段说明**： 
 * - `resource_id`：资源 ID，使用 `@TableId` 注解标记为主键，并设置为 UUID 类型。 
 * - `roomId`：房间 ID，用于标识具体的房间。 
 * - `userId`：用户 ID，用于关联具体用户。 
 * - `trainingScore`：训练得分，整数类型。 
 * - `trainingPercentage`、`skillPercentage`：训练和技能的百分比，字符串类型。 
 * - `skillScore`：技能分数，字符串类型。 
 * - `comment`：用户评论，字符串类型，最大长度限制由枚举类定义。 
 * - `finalScore`：最终得分，整数类型。 
 * ### 枚举类 
 * - 定义了一个内部枚举类 `ScoreValue`，包含多个常量，用于描述与评分相关的固定值或范围（如最小百分比、最大百分比等）。 
 * ### 总结 
 * 该类主要用于数据持久化，提供了基本的 CRUD 操作支持，并通过注解实现了与数据库表的映射关系。同时，通过枚举类规范了评分相关的约束条件。 
**/
@Getter
@Setter
@TableName(value = "cr_score")
public class CrScore extends BaseTable {

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
	 * 用户id
	 */
	private String userId;

	/**
	 * 训练得分
	 */
	private Integer trainingScore;

	/**
	 * 训练百分比
	 */
	private String trainingPercentage;

	/**
	 * 技能分数
	 */
	private String skillScore;

	/**
	 * 技能百分比
	 */
	private String skillPercentage;

	/**
	 * 评论
	 */
	private String comment;

	/**
	 * 最终比分
	 */
	private Integer finalScore;

	public enum ScoreValue {

		MIN_PERCENTAGE(10, "评分最低占比"), MAX_PERCENTAGE(90, "评分最高占比"), SUM_OF_PERCENTAGE_INT(100,
				"评分占比之和"), ABILITY_SCORE_NUM(5, "能力评分数量"), MIN_ABILITY_SCORE_VALUE(0,
						"最先能力评分取值"), MAX_ABILITY_SCORE_VALUE(5,
								"最大能力评分取值"), MAX_COMMENT_LEN(200, "评语最大长度");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ScoreValue(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}

