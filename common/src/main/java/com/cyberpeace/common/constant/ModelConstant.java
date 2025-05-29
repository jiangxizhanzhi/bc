package com.cyberpeace.common.constant;

/**
 * 模型常量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ModelConstant` 的 Java 接口，用于存储与模型相关的常量。以下是简要分析： 
 * 1. **接口作用**： 
 * - `ModelConstant` 是一个接口，通常用于定义一组常量，供其他类使用。 
 * - 接口中的常量默认是 `public static final`，因此可以直接通过接口名访问。 
 * 2. **常量定义**： 
 * - `DELETE_VALUE`：表示逻辑删除的状态值，值为 `0`。 
 * - `NOT_DELETE`：表示非删除的状态值，值为 `1`。 
 * 3. **用途**： 
 * - 这些常量可能用于标识数据是否被逻辑删除，常见于 ORM（如 MyBatis）或数据库操作中。 
 * 4. **代码风格**： 
 * - 注释清晰地说明了每个常量的含义，便于维护和理解。 
 * - 常量命名遵循驼峰命名法，并使用全大写字母以符合 Java 常量命名规范。 
 * 总结：这是一个简单的常量接口，用于统一管理模型中的逻辑删除状态值。 
**/
public interface ModelConstant {

	/**
	 * 逻辑删除
	 *
	 * @see Integer
	 */
	Integer DELETE_VALUE = 0;

	/**
	 * 非删除
	 *
	 * @see Integer
	 */
	Integer NOT_DELETE = 1;
}

