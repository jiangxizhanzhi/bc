package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `DeleteReq` 的 Java 类，该类用于处理删除请求的数据。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.tool.controller.req` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 Swagger 文档，描述 API 模型。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类注释 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * ### 类属性 
 * - **ids**：一个 `List<String>` 类型的属性，用于存储需要删除的 ID 列表。 
 * - **注释**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty(value = "删除ID列表")`：用于描述 `ids` 属性在 Swagger 文档中的说明。 
 * ### 类实现 
 * - 实现了 `Serializable` 接口，意味着该对象可以被序列化，便于在网络上传输或持久化到磁盘。 
 * ### 总结 
 * 这个类的主要目的是作为删除请求的数据传输对象（DTO），包含一个 `ids` 属性，用于存储需要删除的多个 ID。通过 Lombok 注解简化了代码，并且通过 Swagger 注解使该类在生成的 API 文档中具有可读性。 
**/
@Getter
@Setter
@ApiModel
public class DeleteReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * id列表
	 */
	@ApiModelProperty("删除ID列表")
	private List<String> ids;
}

