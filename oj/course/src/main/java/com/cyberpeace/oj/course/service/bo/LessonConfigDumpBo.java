package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 课程配置 dump bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonConfigDumpBo` 的 Java 类，用于表示课程配置的转储（dump）。以下是该类的详细分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`LessonConfigDumpBo` 
 * - **实现接口**：`Serializable`，表示该类的对象可以序列化。 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法。 
 * ### 字段 
 * - **index**：一个整数类型的字段，用于表示某种索引。 
 * - **name**：一个字符串类型的字段，用于表示名称。 
 * - **nodeType**：一个整数类型的字段，用于表示节点类型。 
 * - **children**：一个 `List<LessonConfigDumpBo>` 类型的字段，用于表示子级配置。这是一个递归结构，因为 `LessonConfigDumpBo` 类型的实例可以包含在自身的列表中。 
 * ### 功能 
 * - 这个类主要用于存储和传输课程配置信息。通过递归的 `children` 字段，可以表示多级课程配置结构。 
 * ### 作者和版本信息 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 总结 
 * 这个类是一个简单的数据传输对象（DTO），用于表示课程配置信息，并支持多级嵌套。它使用了 Lombok 来简化代码，减少了样板代码的编写。 
**/
@Getter
@Setter
public class LessonConfigDumpBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 指数
	 *
	 * @see Integer
	 */
	private Integer index;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 节点类型
	 *
	 * @see Integer
	 */
	private Integer nodeType;

	/**
	 * 子级
	 *
	 * @see List<LessonConfigDumpBo>
	 */
	private List<LessonConfigDumpBo> children;

}

