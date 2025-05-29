package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 训练记录
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TrainingRecord` 的 Java 类，用于表示训练记录。以下是简要分析： 
 * 1. **类的功能**： 
 * - 这个类主要用于存储与训练相关的记录信息，包括时间和记录内容。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的注解： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成包含所有字段的构造函数和无参构造函数。 
 * - 使用了 Swagger 的注解 `@ApiModelProperty`：为字段添加描述性注释，方便 API 文档生成。 
 * 3. **字段说明**： 
 * - `time`：记录的时间，类型为 `Date`，用以表示具体的训练时间。 
 * - `record`：记录的具体内容，类型为 `String`，用于描述训练的相关信息。 
 * 4. **设计特点**： 
 * - 简洁明了，通过 Lombok 减少了样板代码。 
 * - 使用了注解增强代码的可读性和扩展性（如 Swagger 注解）。 
 * 总结：这是一个简单的实体类，用于存储训练记录的基本信息，适合在后端系统中作为数据传输对象（DTO）。 
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRecord {

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间")
	private Date time;

	/**
	 * 记录
	 */
	@ApiModelProperty(value = "记录")
	private String record;
}

