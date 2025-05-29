package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景配置响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `SceneConfigRsp` 的 Java 类，用于表示场景配置的响应数据。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是场景配置的响应对象，主要用于 API 返回的数据结构。 
 * 2. **注解与功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，减少冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于接口文档生成。 
 * 3. **字段说明**： 
 * - `nodeCount`：表示节点的数量，类型为 `Integer`。 
 * - `hasScene`：表示是否启用了测试场景，类型为 `Boolean`。 
 * - `resourceOccupiedNums`：表示拓扑中占用的授权节点数量，类型为 `Integer`。 
 * 4. **设计目的**： 
 * - 该类封装了场景配置相关的必要信息，方便在 API 响应中传递给客户端。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装场景配置的响应数据，具有良好的可读性和扩展性。 
**/
@Getter
@Setter
public class SceneConfigRsp {

	/**
	 * 节点计数
	 */
	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	/**
	 * 具有场景
	 */
	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;

	/**
	 * 资源占用数
	 */
	@ApiModelProperty(value = "拓扑中占用的授权节点数量")
	private Integer resourceOccupiedNums;
}

