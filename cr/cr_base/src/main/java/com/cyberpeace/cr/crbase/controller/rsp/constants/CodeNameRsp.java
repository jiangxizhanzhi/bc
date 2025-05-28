/**
 * 该代码定义了一个名为 `CodeNameRsp` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.controller.rsp.constants` 中。以下是代码的主要功能和特点： 
 * 1. **类的功能**： 
 * - 该类用于表示“代号-名称”对的响应数据结构。 
 * - 它包含两个字段：`code`（代号）和 `name`（名称），分别表示代号和对应的名称。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元数据说明，便于生成 API 文档。 
 * 3. **构造方法**： 
 * - 提供了一个带参数的构造函数，用于初始化 `code` 和 `name` 字段。 
 * 4. **设计目的**： 
 * - 该类是一个简单的 POJO（Plain Old Java Object），通常用于作为 API 响应的数据载体。 
 * 总结：这是一个轻量级的 Java 数据模型类，用于封装代号和名称的响应数据，并支持通过 Lombok 和 Swagger 注解简化开发。 
**/
package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 代号名称响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CodeNameRsp {

	/**
	 * 代号
	 */
	@ApiModelProperty("代号")
	Integer code;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	String name;

	public CodeNameRsp(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
}
