package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 简单用户数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SimpleUserData` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示简单的用户数据信息。以下是详细的分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `SimpleUserData` 
 * - **继承**: 实现了 `Serializable` 接口 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 为所有字段生成 getter 方法。 
 * - `@Setter`: 为所有字段生成 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModel`: 表示这是一个模型类。 
 * - `@ApiModelProperty`: 用于描述字段的含义。 
 * ### 字段 
 * - **userId**: 用户ID，类型为 `String`，用 Swagger 注解描述为“用户编号”。 
 * - **userName**: 用户名称，类型为 `String`，用 Swagger 注解描述为“用户名称”。 
 * - **name**: 用户姓名，类型为 `String`，用 Swagger 注解描述为“用户姓名”。 
 * - **organizationId**: 组织ID，类型为 `String`，用 Swagger 注解描述为“组织编号”。 
 * ### 总结 
 * - 这个类的主要目的是封装用户的简单信息，包括用户ID、用户名、用户姓名和组织ID。 
 * - 使用 Lombok 注解简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解方便在 API 文档中展示这些字段的信息。 
 * 这个类可以被其他服务或模块用来传递和处理简单的用户信息。 
**/
@Getter
@Setter
@ApiModel
public class SimpleUserData implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

}

