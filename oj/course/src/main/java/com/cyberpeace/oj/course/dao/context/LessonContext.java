package com.cyberpeace.oj.course.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程背景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonContext` 的 Java 类，该类继承自 `PatternBase`。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.dao.context` 
 * - **类名**：`LessonContext` 
 * - **继承**：`extends PatternBase` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，用于自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * 1. **type** 
 * - **类型**：`Integer` 
 * - **描述**：表示课程背景的类型。 
 * - **注释**：通过 `@see Integer` 引用类型说明。 
 * 2. **courseIds** 
 * - **类型**：`List<String>` 
 * - **描述**：表示与该课程背景相关的课程 ID 列表。 
 * - **注释**：通过 `@see List<String>` 引用类型说明。 
 * ### 元数据 
 * - **作者**：`SN` 
 * - **版本**：`1.0.0` 
 * - **日期**：`2024/11/08` 
 * - **相关类**：`PatternBase` 
 * ### 总结 
 * - `LessonContext` 类用于表示课程背景信息，并包含两个主要属性：`type` 和 `courseIds`。 
 * - `type` 用于标识课程背景的类型，而 `courseIds` 用于存储与该背景相关的课程 ID 列表。 
 * - 该类继承自 `PatternBase`，可能包含了其他通用的属性或方法。 
 * - 使用了 Lombok 注解简化了代码，避免了手动编写 getter 和 setter 方法。 
**/
@Getter
@Setter
public class LessonContext extends PatternBase {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 课程id列表
	 *
	 * @see List<String>
	 */
	private List<String> courseIds;

}

