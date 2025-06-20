package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景展示模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneViewContext` 的 Java 类，用于表示一个场景展示的上下文信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `SceneViewContext` 是一个实现了 `Serializable` 接口的类，主要用于存储与场景展示相关的数据。 
 * 2. **字段说明**： 
 * - `roomId`：表示房间的唯一标识符。 
 * - `localSceneId`：表示本地场景的唯一标识符。 
 * - `mpSceneId`：表示中台场景的唯一标识符。 
 * - `topoId`：表示拓扑的唯一标识符。 
 * - `topoData`：存储拓扑的详细数据，类型为 `JSONObject`。 
 * - `sceneData`：存储场景的详细数据，类型为 `JSONObject`。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的getter和setter方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供描述信息，便于生成 API 文档。 
 * 4. **设计目的**： 
 * - 该类的设计目的是作为一个数据传输对象（DTO），在不同的模块或服务之间传递场景展示所需的信息。 
 * 5. **依赖库**： 
 * - 使用了 Alibaba 的 `fastjson` 库来处理 JSON 数据。 
 * - 使用了 Lombok 和 Swagger 注解库来简化代码和增强可读性。 
 * 总结：`SceneViewContext` 是一个简单的数据传输类，用于封装场景展示所需的各类信息，支持序列化，并通过注解增强了代码的可维护性和文档生成能力。 
**/
@Getter
@Setter
public class SceneViewContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 房间id
	 */
	@ApiModelProperty("房间id")
	private String roomId;

	/**
	 * 当地场景id
	 */
	@ApiModelProperty("场景管理器id")
	private String localSceneId;

	/**
	 * 中台场景id
	 */
	@ApiModelProperty("场景id")
	private String mpSceneId;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑id")
	private String topoId;

	/**
	 * 拓扑数据
	 */
	@ApiModelProperty("拓扑模板数据")
	private JSONObject topoData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;
}

