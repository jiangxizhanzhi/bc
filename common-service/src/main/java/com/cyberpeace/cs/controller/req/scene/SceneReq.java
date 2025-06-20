package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneReq` 的 Java 类，用于表示场景请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求对象，用于封装与场景相关的请求数据。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化，可能用于网络传输或持久化。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段 `localSceneId` 提供元信息，描述其用途为“场景管理器ID”。 
 * 3. **主要字段**： 
 * - `localSceneId`：字符串类型，表示本地场景的唯一标识符。 
 * 4. **设计目的**： 
 * - 该类旨在作为 API 请求的一部分，提供场景管理所需的基本信息。 
 * 总结：`SceneReq` 是一个简单的 POJO（Plain Old Java Object），用于传递场景相关的信息，支持序列化且具备良好的注解支持。 
**/
@Getter
@Setter
public class SceneReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 当地场景id
	 */
	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;
}

