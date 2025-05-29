package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 类别新增博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CategoryAddBo` 的 Java 类，该类用于表示在系统中添加类别时需要的信息。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `CategoryAddBo` 
 * - **继承关系**: 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 类上有 Javadoc 文档注释，描述了类的功能和作者信息。 
 * ### 属性 
 * - **resourceId**: 字符串类型，表示资源ID。 
 * - **name**: 字符串类型，表示名称。 
 * - **parentId**: 字符串类型，表示父ID。 
 * - **courseType**: 整数类型，表示课程类型。 
 * ### 总结 
 * - `CategoryAddBo` 类主要用于封装添加类别时所需的信息，如资源ID、名称、父ID和课程类型。 
 * - 通过使用 Lombok 的注解，简化了代码，减少了样板代码。 
 * - 类实现了 `Serializable` 接口，说明它可能需要在网络传输或持久化存储时被序列化。 
 * 这个类的设计目的是为了在添加类别时提供必要的数据结构，并且通过实现 `Serializable` 接口，便于在分布式系统或持久化场景中的使用。 
**/
@Getter
@Setter
public class CategoryAddBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 父母id
	 *
	 * @see String
	 */
	private String parentId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	private Integer courseType;

}

