/**
 * 这段代码定义了一个名为 `FlowDownloadRsp` 的 Java 类，该类用于表示流下载操作的响应。以下是对该代码的简要分析： 
 * ### 类名及包名 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 这个包名表明该类位于一个与控制器响应相关的包中。 
 * - **类名**：`FlowDownloadRsp` 
 * - 类名清晰地表达了这是一个与流下载响应相关的类。 
 * ### 导入的类 
 * - **Serializable**：表示这个类实现了 `Serializable` 接口，这意味着它可以被序列化，通常用于在网络上传输对象或将其持久化到存储介质中。 
 * - **ApiModelProperty**：来自 Swagger 注解库，用于生成 API 文档，标记字段在 API 中如何展示。 
 * - **Getter 和 Setter 注解**：来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 类注释描述了该类的功能，即“流下载响应”。 
 * ### 字段 
 * - **filePath**：类型为 `String`，表示文件的路径。使用 `@ApiModelProperty` 注解来描述这个字段的作用。 
 * ### 构造函数 
 * - 提供了一个带参数的构造函数，用于初始化 `filePath` 字段。 
 * ### 总结 
 * - `FlowDownloadRsp` 是一个简单的数据传输对象（DTO），用于封装流下载操作的响应信息。 
 * - 它包含一个 `filePath` 字段，并且提供了相应的 getter 和 setter 方法。 
 * - 使用了 Lombok 来简化代码，使得不需要手动编写 getter 和 setter 方法。 
 * - 包含了 Swagger 注解，方便生成 API 文档。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 流下载响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowDownloadRsp implements Serializable {

	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "文件路径")
	private String filePath;

	public FlowDownloadRsp(String filePath) {
		this.filePath = filePath;
	}

}
