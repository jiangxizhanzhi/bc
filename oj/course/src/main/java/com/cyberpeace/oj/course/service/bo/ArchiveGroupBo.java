package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 归档组 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ArchiveGroupBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类被用作业务对象（BO），主要用于数据传输或存储。以下是对代码的简要分析： 
 * ### 包声明和导入 
 * - `package com.cyberpeace.oj.course.service.bo;`：声明了类所在的包。 
 * - 导入了 `java.io.Serializable` 接口，用于实现序列化功能。 
 * - 导入了 `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 注解，用于生成 API 文档。 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 类注释说明了 `ArchiveGroupBo` 是一个归档组业务对象，并包含了作者、版本和日期信息。 
 * ### 类属性 
 * - `groupNo`: 一个 `Integer` 类型的变量，表示组号。 
 * - `groupUserIds`: 一个 `String` 类型的变量，表示群用户 ID 列表。 
 * - `resourceId`: 一个 `String` 类型的变量，表示资源 ID。 
 * ### 属性注释 
 * - 每个属性都有对应的 Javadoc 注释，描述了属性的用途。 
 * ### 注解 
 * - `@ApiModel`: 表示这是一个 Swagger API 模型。 
 * - `@ApiModelProperty`: 为每个属性添加描述，便于生成 API 文档。 
 * ### 总结 
 * - 这是一个简单的数据传输对象（DTO）或业务对象（BO），用于封装与“归档组”相关的数据。 
 * - 类中的属性通过 Lombok 的 `@Getter` 和 `@Setter` 自动实现了访问器方法。 
 * - 使用 Swagger 注解来帮助生成 API 文档。 
 * - 实现了 `Serializable` 接口，表明它可以被序列化，可能用于网络传输或持久化存储。 
 * 这个类的设计简洁明了，适合在服务层中使用，特别是在需要传递或存储“归档组”相关信息时。 
**/
@Getter
@Setter
@ApiModel
public class ArchiveGroupBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 组号
	 *
	 * @see Integer
	 */
	@ApiModelProperty("组号")
	private Integer groupNo;

	/**
	 * 群用户id列表
	 *
	 * @see String
	 */
	@ApiModelProperty("组员编号")
	private String groupUserIds;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

}

