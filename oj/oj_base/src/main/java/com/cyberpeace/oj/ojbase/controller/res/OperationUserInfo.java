package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 实操用户信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `OperationUserInfo` 的 Java 类，用于表示操作用户的详细信息。以下是对其主要特性的简要分析： 
 * 1. **包声明**：该类位于 `com.cyberpeace.oj.ojbase.controller.res` 包中。 
 * 2. **依赖库**： 
 * - 使用了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 注解 (`io.swagger.annotations`) 来为 API 文档提供元数据。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化存储。 
 * 3. **注释**： 
 * - 类上使用了 `@ApiModel` 注解，说明这是一个用于 Swagger 文档的模型类。 
 * - 每个属性上都有 `@ApiModelProperty` 注解，用于描述属性在 API 中的意义。 
 * 4. **属性**： 
 * - `id`：表示用户的唯一标识符，类型为字符串。 
 * - `name`：表示用户的名称，类型为字符串。 
 * - `username`：表示用户的登录名，类型为字符串。 
 * - `orgpaths`：表示用户的组织信息，类型为二维列表（`List<List<String>>`），每个内部列表可能表示一个层级的组织路径。 
 * 5. **功能**： 
 * - 该类主要用于封装与操作用户相关的数据信息，方便在系统内部进行传递和处理。 
 * - 通过 Swagger 注解，可以帮助开发者生成文档，以便于其他开发者理解如何使用这些数据结构。 
 * 总体而言，`OperationUserInfo` 类是一个简单的数据传输对象（DTO），用于在系统内传输操作用户的详细信息，并且支持序列化以便于网络传输和持久化。 
**/
@Getter
@Setter
@ApiModel
public class OperationUserInfo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String id;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 组织路径
	 *
	 * @see List<List<String>>
	 */
	@ApiModelProperty("组织信息")
	private List<List<String>> orgpaths;

}

