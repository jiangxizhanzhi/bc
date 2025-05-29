package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权的教员
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ShareTeacher` 的 Java 类，用于表示授权的教员信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这个类主要用来存储和管理教员的相关信息。 
 * - 它包含了教员的基本属性，如 ID、姓名、所属组织及其名称、用户名等。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码量。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，方便生成 API 文档时使用。 
 * 3. **字段说明**： 
 * - `id`：教员的唯一标识符。 
 * - `name`：教员的姓名。 
 * - `organization`：教员所属组织的 ID。 
 * - `organizationNameAll`：教员所属组织的完整名称。 
 * - `username`：教员的用户名。 
 * 4. **设计目的**： 
 * - 这个类可能是某个系统中用于处理教员信息的核心数据结构，便于在不同模块之间传递和使用教员信息。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，主要用于存储和传输教员相关信息，支持自动化的 getter 和 setter 方法，并通过注解增强了其可读性和文档化能力。 
**/
@Getter
@Setter
public class ShareTeacher {

	/**
	 * id
	 */
	@ApiModelProperty(value = "用户id，resource_id")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 组织
	 */
	@ApiModelProperty(value = "组织id")
	private String organization;

	/**
	 * 组织名称都
	 */
	@ApiModelProperty(value = "组织名称")
	private String organizationNameAll;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

}

