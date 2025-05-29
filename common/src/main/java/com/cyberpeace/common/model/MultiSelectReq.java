package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 多查询请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MultiSelectReq` 的 Java 类，用于表示多查询请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个数据传输对象（DTO），用于接收前端传来的多查询请求参数。 
 * - 它包含一个字段 `ids`，用于存储需要查询的 ID 列表。 
 * 2. **关键特性**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 借助 `io.swagger.annotations.ApiModel` 和 `@ApiModelProperty` 注解，为 API 文档生成提供支持，便于接口文档生成工具解析。 
 * 3. **字段说明**： 
 * - `ids`: 一个字符串类型的列表，表示需要查询的一组 ID。 
 * 4. **技术细节**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * 总结：这是一个简洁且功能明确的 DTO 类，主要用于处理多 ID 查询场景，并支持自动化的代码生成与 API 文档生成。 
**/
@Data
@ApiModel
public class MultiSelectReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty
	private List<String> ids;
}

