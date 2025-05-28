/**
 * 这段代码定义了一个名为 `SceneNodeReq` 的 Java 类，用于表示场景节点的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是 `com.cyberpeace.cs.controller.req.scene` 包的一部分，用于处理与场景节点相关的请求数据。 
 * 2. **继承与注解**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成了字段的getter和setter方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述信息，便于接口文档生成。 
 * 3. **字段说明**： 
 * - `localSceneId`：表示本地场景的唯一标识符，通过 `@ApiModelProperty` 描述其含义。 
 * - `nodeId`：表示节点的唯一标识符，同样有对应的描述信息。 
 * 4. **设计目的**： 
 * - 该类主要用于接收前端传递的场景节点相关数据，作为请求参数使用。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装场景节点的请求参数，支持序列化并带有清晰的注释和描述。 
**/
package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景节点请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneNodeReq implements Serializable {

	/**
	 * 当地场景id
	 */
	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

}