/**
 * 这段代码定义了一个名为 `RbUserData` 的 Java 类，用于表示红蓝用户的详细信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储与红蓝用户相关的信息，包括用户的基本属性和关联的数据。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `logo`：用户或组织的图标，表示用户的身份或所属组织的标志。 
 * - `repName`：用户代表的名称，通常为用户的姓名。 
 * - `resourceId`：资源 ID，作为用户唯一标识符。 
 * - `groupName`：用户所在的群组名称，表示用户所属的用户组。 
 * - `roleName`：用户的角色名称，表示用户在系统中的权限或职责。 
 * 4. **设计特点**： 
 * - 类的设计简单明了，专注于存储用户的基本信息和关联数据。 
 * - 使用注解提高了代码的可读性和开发效率，同时便于集成到 Swagger 等工具中。 
 * 总结：这是一个轻量级的数据传输对象（DTO），用于封装红蓝用户的相关信息，方便在系统中传递和使用。 
**/
package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 红蓝用户数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class RbUserData {

	/**
	 * 图标
	 */
	@ApiModelProperty(value = "LOGO")
	private String logo;

	/**
	 * 代表名称
	 */
	@ApiModelProperty(value = "用户姓名")
	private String repName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "用户编号")
	private String resourceId;

	/**
	 * 群名称
	 */
	@ApiModelProperty(value = "用户组名")
	private String groupName;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;
}
