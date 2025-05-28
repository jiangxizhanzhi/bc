/**
 * 这段代码定义了一个名为 `DeleteResourceReq` 的 Java 类，用于处理删除资源的请求体。以下是详细的分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.ojbase.controller.req; 
 * ``` 
 * - 这个类位于 `com.cyberpeace.oj.ojbase.controller.req` 包中。 
 * ### 导入的包 
 * ```java 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * import java.util.List; 
 * ``` 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 Swagger 文档。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable` 接口使得该类可以被序列化。 
 * - `java.util.List` 用于存储资源 ID 列表。 
 * ### 类定义 
 * ```java 
 * @ApiModel 
 * @Getter 
 * @Setter 
 * public class DeleteResourceReq implements Serializable { 
 * ``` 
 * - `@ApiModel` 注解用于描述这个类是关于什么的。 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成 getter 和 setter 方法。 
 * - `Serializable` 接口使得这个类可以被序列化。 
 * ### 成员变量 
 * ```java 
 * @ApiModelProperty("资源数据编号") 
 * private List<String> resourceIds; 
 * ``` 
 * - `resourceIds` 是一个 `List<String>` 类型的成员变量，用于存储要删除的资源 ID 列表。 
 * - `@ApiModelProperty("资源数据编号")` 注解用于在 Swagger 文档中描述这个字段。 
 * ### 总结 
 * - `DeleteResourceReq` 类主要用于接收删除资源的请求体，其中包含一个 `resourceIds` 字段，表示需要删除的资源 ID 列表。 
 * - 使用了 Lombok 的注解来简化代码，同时使用 Swagger 注解来生成文档。 
 * - 该类实现了 `Serializable` 接口，因此可以在网络传输或持久化时进行序列化。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 删除资源请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class DeleteResourceReq implements Serializable {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("资源数据编号")
	private List<String> resourceIds;

}
