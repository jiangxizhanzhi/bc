/**
 * 这段代码定义了一个名为 `NoteListQuery` 的类，该类继承自 `PatternBase` 类。这个类主要用于查询注释列表，并包含了一些用于过滤查询结果的属性。以下是对这段代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.oj.ojbase.dao.context` 
 * - **导入**： 
 * - `java.util.List`：用于存储用户ID列表。 
 * - `com.cyberpeace.common.model.PatternBase`：继承自该类。 
 * ### 类声明 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：自动为所有字段生成 getter 和 setter 方法。 
 * - `@Builder`：允许使用构建器模式创建对象实例。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别提供带有所有参数的构造函数和无参构造函数。 
 * ### 类描述 
 * - 类名：`NoteListQuery` 
 * - 作者：`SN` 
 * - 版本：`1.0.0` 
 * - 创建日期：`2024/11/08` 
 * ### 类中的属性 
 * - **userIds**：一个 `List<String>` 类型的变量，用于存储用户ID列表。 
 * - **noteType**：一个 `Integer` 类型的变量，表示注释类型。 
 * - **businessType**：一个 `Integer` 类型的变量，表示业务类型。 
 * - **businessId**：一个 `String` 类型的变量，表示业务ID。 
 * ### 总结 
 * - 这个类的主要目的是封装查询注释列表时所需的参数。 
 * - 它继承了 `PatternBase` 类，可能包含一些通用的属性或方法。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码的编写。 
 * - 属性包括用户ID列表、注释类型、业务类型和业务ID，这些属性可以用来过滤和筛选注释列表。 
**/
package com.cyberpeace.oj.ojbase.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 注释列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteListQuery extends PatternBase {

	/**
	 * 用户id列表
	 *
	 * @see List<String>
	 */
	private List<String> userIds;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

}
