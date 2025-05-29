package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景节点老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneNodeOpsReq` 的 Java 类，用于表示场景节点操作请求的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，主要用于接收与场景节点相关的操作请求数据。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * 2. **字段说明**： 
 * - `resourceId`：表示当前业务的唯一标识符，使用了 `@NotBlank` 注解确保其非空。 
 * - `nodeId`：表示场景节点的唯一标识符，同样使用了 `@NotBlank` 注解确保其非空。 
 * 3. **注解说明**： 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，描述字段的含义和是否必填。 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * 4. **设计目的**： 
 * - 该类的设计目的是为了标准化场景节点操作请求的数据结构，便于前端与后端的数据交互。 
 * 总体而言，这是一个简单的数据传输对象（DTO），专注于封装和验证请求参数。 
**/
@Getter
@Setter
public class SceneNodeOpsReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "当前业务id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}

