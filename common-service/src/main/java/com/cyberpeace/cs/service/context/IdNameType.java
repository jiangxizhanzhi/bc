package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * id名称类型
 *
 * @author SN
 * @date 2023/05/18
 */
/**
 * 这段代码定义了一个名为 `IdNameType` 的 Java 类，用于表示包含 ID、名称和类型的简单数据结构。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个简单的 POJO（Plain Old Java Object），主要用于存储与 "id"、"name" 和 "type" 相关的数据。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化，通常用于在网络传输或持久化时使用。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `id`：字符串类型，表示某个实体的唯一标识符。 
 * - `name`：字符串类型，表示实体的名称。 
 * - `type`：整数类型，表示实体的类别或类型。 
 * 4. **设计目的**： 
 * - 该类可能作为数据传输对象（DTO）或实体类，在系统中传递或存储相关信息。 
 * 总结来说，这是一个轻量级的 Java Bean，用于封装基本的 ID、名称和类型信息，并支持序列化和文档生成。 
**/
@Getter
@Setter
public class IdNameType implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "ID")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;
}

