package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 课纸评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPaperEvaluate` 的 Java 类，用于表示课纸评估实体。以下是该类的详细分析： 
 * ### 包和导入 
 * - **包名**：`com.cyberpeace.oj.course.dao.entity` 
 * - **导入的类和库**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 
 * - `com.baomidou.mybatisplus.annotation.TableId` 
 * - `com.baomidou.mybatisplus.annotation.TableName` 
 * - `com.cyberpeace.common.model.BaseTable` 
 * - `lombok.Getter` 
 * - `lombok.Setter` 
 * ### 类定义 
 * - **类名**：`LessonPaperEvaluate` 
 * - **继承关系**：继承自 `BaseTable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成属性的 getter 和 setter 方法。 
 * - `@TableName`：指定该实体对应数据库表的名称 `oj_lesson_paper_evaluate` 
 * ### 属性 
 * - `resourceId`：资源ID，类型为 `String`，使用 MyBatis Plus 的 `@TableId` 注解标识为主键，并设置类型为 UUID。 
 * - `lessonId`：课程ID，类型为 `String` 
 * - `score`：得分，类型为 `Double` 
 * - `totalScore`：总分，类型为 `Double` 
 * - `record`：记录，类型为 `String` 
 * - `submitUserId`：提交用户的ID，类型为 `String` 
 * - `level`：水平，类型为 `Integer` 
 * - `accuracy`：准确性，类型为 `String` 
 * - `corrector`：校正者，类型为 `String` 
 * ### 注释 
 * - 类注释说明了这是一个课纸评估类，作者为 SN，版本为 1.0.0，创建日期为 2024年11月8日。 
 * - 每个属性都有详细的注释，描述了属性的作用和数据类型。 
 * ### 总结 
 * - `LessonPaperEvaluate` 是一个用于表示课纸评估的实体类。 
 * - 使用了 Lombok 来简化 getter 和 setter 方法的生成。 
 * - 通过 MyBatis Plus 的注解来映射数据库表和字段。 
 * - 该类主要用于持久化到数据库中，实现课纸评估信息的存储和管理。 
**/
@Getter
@Setter
@TableName("oj_lesson_paper_evaluate")
public class LessonPaperEvaluate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	private Double score;

	/**
	 * 总分
	 *
	 * @see Double
	 */
	private Double totalScore;

	/**
	 * 记录
	 *
	 * @see String
	 */
	private String record;

	/**
	 * 提交用户id
	 *
	 * @see String
	 */
	private String submitUserId;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	private Integer level;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	private String accuracy;

	/**
	 * 校正者
	 *
	 * @see String
	 */
	private String corrector;

}

