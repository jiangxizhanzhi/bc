package com.cyberpeace.oj.ojbase.dao.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 注意唯一上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteUniqueContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是存储与某个唯一上下文相关的数据。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.ojbase.dao.context` 
 * - **导入语句**： 
 * - `lombok.*` 包中的所有类，用于简化类的定义。 
 * - `java.io.Serializable` 接口，使对象能够序列化。 
 * ### 类注解 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：Lombok 注解，分别生成包含所有属性的构造函数和无参构造函数。 
 * - `@Builder`：Lombok 注解，为类生成一个构建器模式的方法，方便对象的创建。 
 * ### 类属性 
 * - `private Integer noteType;`：表示注释类型。 
 * - `private Integer businessType;`：表示业务类型。 
 * - `private String userId;`：表示用户 ID。 
 * - `private String businessId;`：表示业务 ID。 
 * ### 类文档 
 * - 文档注释详细描述了类的目的和版本信息。 
 * - 每个属性都有详细的文档注释，说明了其用途和可能的数据类型。 
 * ### 总结 
 * `NoteUniqueContext` 类是一个简单的数据传输对象（DTO），用于在系统中传递关于某个唯一上下文的信息。通过 Lombok 注解，减少了样板代码，使得类更加简洁易读。该类主要用于存储注释类型、业务类型、用户 ID 和业务 ID 等信息，并且可以被序列化以便在网络上传输或持久化到数据库。 
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteUniqueContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

}

