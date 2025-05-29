package com.cyberpeace.cr.crbase.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务基地
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MissionBase` 的 Java 实体类，用于表示任务基地的相关信息。以下是代码的主要功能和特点： 
 * 1. **继承与依赖**： 
 * - `MissionBase` 继承自 `BaseTable`，表明它是一个数据库表对应的实体类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - 包含多个字段，如 `score`、`resourceId`、`title`、`description` 等，每个字段都有相应的注释说明其用途。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述字段的含义，便于生成 API 文档。 
 * 3. **枚举类型**： 
 * - 定义了一个内部枚举 `MissionType`，包含多种任务类型（如判断、单选、多选等）及其对应的代码值。 
 * - 提供了一些静态列表（如 `OBJECTIVE_TYPE_LIST`、`ANSWER_NULLABLE_TYPE_LIST` 等），用于分类不同的任务类型。 
 * 4. **方法**： 
 * - `isObjective()`：检查当前任务是否属于客观题类型。 
 * - `isObjective(Integer type)`：静态方法，根据传入的任务类型判断是否为客观题。 
 * 5. **设计目的**： 
 * - 该类主要用于存储和管理任务相关的数据，支持多种任务类型，并提供了方便的方法来操作这些数据。 
 * 总体而言，这段代码结构清晰，使用了现代 Java 编程的最佳实践，包括 Lombok 和枚举类型的应用，使得代码易于维护和扩展。 
**/
@Getter
@Setter
public class MissionBase extends BaseTable {

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分值")
	protected Integer score;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id")
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;

	/**
	 * 技能点
	 */
	@ApiModelProperty(value = "技能点")
	private String skillPoints;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "任务答案")
	private String answer;

	/**
	 * 选择
	 */
	@ApiModelProperty(value = "选项")
	private String option;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别")
	private String categoryId;

	/**
	 * 写入向上
	 */
	@ApiModelProperty(value = "题解")
	private String writeUp;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty(value = "创建者id")
	private String createUserId;

	public enum MissionType {

		JUDGE(0, "判断"), SINGLE_CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
				"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

		public static final List<Integer> OBJECTIVE_TYPE_LIST = new ArrayList<>(
				Arrays.asList(JUDGE.code, SINGLE_CHOICE.code, MULTIPLE_CHOICE.code, FILE.code));

		public static final List<Integer> ANSWER_NULLABLE_TYPE_LIST = new ArrayList<>(
				Arrays.asList(FAQ.code, ATTACK.code, DEFENCE.code, FILE.code));

		public static final List<Integer> HAS_OPTION_TYPE_LIST = new ArrayList<>(
				Arrays.asList(SINGLE_CHOICE.code, MULTIPLE_CHOICE.code));

		@Getter
		private Integer code;

		@Getter
		private String desc;

		MissionType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public Boolean isObjective() {
		Integer missionType = getType();
		return isObjective(missionType);
	}

	public static Boolean isObjective(Integer type) {
		if (type == null) {
			return false;
		}
		boolean result = type.intValue() == MissionType.JUDGE.getCode();
		result = result || (type.intValue() == MissionType.SINGLE_CHOICE.getCode());
		result = result || (type.intValue() == MissionType.MULTIPLE_CHOICE.getCode());
		result = result || (type.intValue() == MissionType.FILE.getCode());

		return result;
	}
}

