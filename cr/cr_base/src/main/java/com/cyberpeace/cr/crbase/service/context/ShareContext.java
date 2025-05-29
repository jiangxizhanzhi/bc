package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ShareContext` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.service.context` 中。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个授权模型，用于描述与共享相关的上下文信息。 
 * - 它包含三个主要属性：`ids`、`shareMode` 和 `shareTeachers`。 
 * 2. **属性说明**： 
 * - `ids`：一个字符串列表，表示一组模板 ID，注解 `@ApiModelProperty` 描述其为“模板id”。 
 * - `shareMode`：一个整型变量，表示共享模式，注解 `@ApiModelProperty` 描述其为“授权类别”。 
 * - `shareTeachers`：一个字符串列表，表示分享的教师 ID，注解 `@ApiModelProperty` 描述其为“授权人员id”。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为属性提供元数据，便于生成 API 文档。 
 * 4. **设计意图**： 
 * - 该类旨在封装与共享相关的上下文信息，方便在系统中传递和使用这些数据。 
 * 总体来说，这是一个简单的数据传输对象（DTO），主要用于存储和传输共享相关的信息。 
**/
@Getter
@Setter
public class ShareContext {

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "模板id")
	private List<String> ids;

	/**
	 * 共享模式
	 */
	@ApiModelProperty(value = "授权类别")
	private Integer shareMode;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权人员id")
	private List<String> shareTeachers;

}

