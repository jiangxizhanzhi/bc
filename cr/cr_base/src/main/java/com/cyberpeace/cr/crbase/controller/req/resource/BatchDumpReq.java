package com.cyberpeace.cr.crbase.controller.req.resource;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 批量转 储请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `BatchDumpReq` 的 Java 类，用于表示批量转储请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，主要用于接收批量转储操作所需的数据。 
 * 2. **关键特性**： 
 * - 实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * - 使用了 Lombok 注解： 
 * - `@Getter` 和 `@Setter` 自动生成属性的 getter 和 setter 方法。 
 * - Swagger 注解 `@ApiModelProperty` 用于描述接口文档中字段的含义。 
 * 3. **主要属性**： 
 * - `ids`：一个字符串类型的列表，用于存储多个 ID，描述为“模板ID列表”。 
 * 4. **设计目的**： 
 * - 该类旨在简化代码编写，通过注解减少样板代码，同时支持序列化和接口文档生成。 
 * 总结：这是一个简单的数据传输对象（DTO），用于接收批量转储操作所需的 ID 列表。 
**/
@Getter
@Setter
public class BatchDumpReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * id列表
	 */
	@ApiModelProperty("模板id列表")
	List<String> ids;
}

