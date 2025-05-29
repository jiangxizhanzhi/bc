package com.cyberpeace.common.model;

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
 * 这段代码定义了一个名为 `DeleteReq` 的 Java 类，用于表示删除操作的请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * 该类是为删除操作设计的请求体模型，包含一个字段 `ids`，用于存储需要删除的 ID 列表。 
 * 2. **依赖的库**： 
 * - 使用了 `lombok` 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 `io.swagger.annotations` 包中的注解 `@ApiModel` 和 `@ApiModelProperty`，用于生成 API 文档。 
 * 3. **关键字段**： 
 * - `ids`：一个 `List<String>` 类型的字段，表示需要删除的 ID 列表，并通过 `@ApiModelProperty` 注解描述其用途。 
 * 4. **实现接口**： 
 * 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 5. **注释**： 
 * 类和字段都有详细的注释，便于开发者理解其用途。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于接收删除操作所需的 ID 列表。 
**/
@Getter
@Setter
@ApiModel
public class DeleteReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;
}

