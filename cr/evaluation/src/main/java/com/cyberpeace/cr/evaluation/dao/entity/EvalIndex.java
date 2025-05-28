/**
 * 这段代码定义了一个名为 `EvalIndex` 的 Java 类，用于表示评估指标。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId` 用于 MyBatis Plus 的主键配置。 
 * - `com.baomidou.mybatisplus.annotation.TableName` 用于指定数据库表名。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum` 用于定义枚举接口。 
 * - `com.cyberpeace.common.model.BaseTable` 是一个基类，可能包含一些通用字段或方法。 
 * - `lombok` 注解（`@Getter`, `@Setter`）用于简化 getter 和 setter 方法的生成。 
 * ### 类定义 
 * - `EvalIndex` 继承自 `BaseTable`。 
 * - 使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - `resource_id`: 唯一标识符，类型为 UUID。 
 * - `name`: 指标的名称。 
 * - `description`: 指标的描述。 
 * - `level`: 指标的级别，取值为 1、2 或 3。 
 * - `evalSystemId`: 体系 ID。 
 * - `evalIndicatorId`: 三级指标项 ID，默认为空字符串。 
 * - `type`: 归一化类型，可选值包括百分比、周期、延时。 
 * - `aggregatingAlgorithmId`: 聚合算法 ID。 
 * - `aggregatingAlgorithmParams`: 聚合算法参数。 
 * - `childrenIds`: 子指标 ID。 
 * - `matrixData`: 矩阵内容。 
 * ### 枚举定义 
 * - `Level` 枚举实现 `GenericEnum` 接口，用于表示指标的不同级别。 
 * - `FIRST`, `SECOND`, `THIRD` 分别对应一级、二级和三级指标。 
 * - 使用 Lombok 注解 `@Getter` 自动生成枚举值的 `code` 和 `desc` 的 getter 方法。 
 * ### 总结 
 * 该类主要用于表示评估指标的相关信息，并提供了必要的属性和方法来支持这些功能。通过继承 `BaseTable` 和使用 Lombok 注解，该类简化了代码的编写和维护。枚举 `Level` 提供了标准化的指标级别表示。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 
 * @TableName cr_eval_index
 */
@TableName(value = "cr_eval_index")
@Getter
@Setter
public class EvalIndex extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 指标级别：1、2、3
	 */
	private Integer level;

	/**
	 * 体系id
	 */
	private String evalSystemId;

	/**
	 * 三级指标项id,默认为空串
	 */
	private String evalIndicatorId = "";

	/**
	 * 归一化类型, 百分比:1,周期:2,延时:3
	 */
	private Integer type;

	/**
	 * 聚合算法id
	 */
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	private String aggregatingAlgorithmParams;

	/**
	 * 子指标id
	 */
	private String childrenIds;

	public enum Level implements GenericEnum {

		FIRST(1, "一级指标"), SECOND(2, "二级指标"), THIRD(3, "三级指标");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Level(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	/**
	 * 矩阵内容
	 */
	private String matrixData;
}