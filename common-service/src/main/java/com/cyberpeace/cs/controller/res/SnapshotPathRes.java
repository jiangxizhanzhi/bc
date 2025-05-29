package com.cyberpeace.cs.controller.res;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 快照路径结果
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SnapshotPathRes` 的 Java 类，用于表示快照路径的结果。以下是对其的简要分析： 
 * 1. **包声明**：类位于 `com.cyberpeace.cs.controller.res` 包中。 
 * 2. **导入的类**： 
 * - 使用了 `com.alibaba.fastjson.JSONObject` 表示路径数据为 JSON 对象。 
 * - 使用了 `io.swagger.annotations.ApiModel` 和 `ApiModelProperty` 注解，用于生成 API 文档。 
 * - 使用了 Lombok 提供的 `@Getter`, `@Setter` 注解简化属性的 getter 和 setter 方法。 
 * 3. **类的功能**： 
 * - 类实现了 `Serializable` 接口，表明它可以被序列化，通常用于在网络传输或持久化时使用。 
 * - 它有两个主要字段： 
 * - `count`：表示快照的数量，类型为 `Integer`。 
 * - `path`：表示快照路径，类型为 `JSONObject`，并且是一个树形结构。 
 * 4. **注解说明**： 
 * - `@ApiModel`：标记该类作为一个模型类，用于 Swagger 自动生成文档。 
 * - `@ApiModelProperty`：为每个字段提供描述信息，便于 API 文档生成。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于存储和传递快照的相关信息，支持序列化，并通过注解增强了其可读性和文档生成能力。 
**/
@Getter
@Setter
@ApiModel
public class SnapshotPathRes implements Serializable {

	/**
	 * 计数
	 */
	@ApiModelProperty("快照数量")
	private Integer count;

	/**
	 * 路径
	 */
	@ApiModelProperty("快照路径 树形结构")
	private JSONObject path;

}

