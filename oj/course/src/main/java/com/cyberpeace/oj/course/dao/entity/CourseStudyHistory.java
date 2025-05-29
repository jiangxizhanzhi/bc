package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课程学习历史
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseStudyHistory` 的 Java 类，用于表示课程学习历史。以下是对其主要特性和功能的简要分析： 
 * ### 包声明 
 * - `package com.cyberpeace.oj.course.dao.entity;` 声明了这个类所在的包。 
 * ### 导入的类 
 * - `com.baomidou.mybatisplus.annotation.IdType`, `com.baomidou.mybatisplus.annotation.TableId`, `com.baomidou.mybatisplus.annotation.TableName`：这些是 MyBatis Plus 框架提供的注解，用于数据库表映射。 
 * - `com.cyberpeace.common.model.BaseTable`：这是一个基类，`CourseStudyHistory` 继承自该类。 
 * - `lombok.*`：Lombok 提供的注解，用于简化 Java 类的编写，如生成 getter 和 setter 方法。 
 * ### 类声明 
 * - `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` 是 Lombok 注解，分别用于生成类的所有属性的 getter 和 setter 方法、无参构造函数以及全参构造函数。 
 * - `@TableName("oj_course_study_history")` 表示该类对应的数据库表名。 
 * - `public class CourseStudyHistory extends BaseTable` 声明了 `CourseStudyHistory` 类继承自 `BaseTable` 类，并且是一个公共类。 
 * ### 属性 
 * - `private String resourceId;`：资源ID，用于标识具体的资源。 
 * - `private String courseId;`：课程ID，用于标识具体的课程。 
 * - `private String courseName;`：课程名称，用于显示课程的名称。 
 * - `private String userId;`：用户ID，用于标识具体的学习用户。 
 * ### 总结 
 * - 这个类主要用于表示用户的课程学习历史记录。 
 * - 它通过继承 `BaseTable` 类来获取一些通用的字段（如创建时间、更新时间等）。 
 * - 使用 MyBatis Plus 的注解来映射数据库表结构。 
 * - Lombok 注解减少了样板代码，使代码更加简洁。 
 * 这个类的设计符合面向对象的原则，提供了清晰的数据模型来存储课程学习的历史信息。 
**/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_course_study_history")
public class CourseStudyHistory extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	private String courseName;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;



}

