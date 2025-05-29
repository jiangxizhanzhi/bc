package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景开始从模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneStartFromTemplateReq` 的 Java 类，用于表示从场景模板启动场景时的请求参数。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个请求体类，用于接收从场景模板启动场景所需的参数。 
 * - 它实现了 `Serializable` 接口，表明它可以被序列化，通常用于网络传输或持久化。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述 API 文档中该字段的意义和是否必填。 
 * 3. **主要字段**： 
 * - `templateId`：表示场景模板的唯一标识符，使用 `@ApiModelProperty` 标记为必填字段。 
 * 4. **设计目的**： 
 * - 该类的设计目的是封装从场景模板启动场景所需的基本信息，便于在前后端交互中传递数据。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，主要用于接收从场景模板启动场景的请求参数，支持序列化，并通过注解增强了代码的可读性和文档生成能力。 
**/
@Getter
@Setter
public class SceneStartFromTemplateReq implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "场景模板id", required = true)
	private String templateId;
}

