package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskRsp` 的 Java 类，用于表示评估任务的响应。以下是该类的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.rsp; 
 * ``` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * ### 导入的类 
 * ```java 
 * import java.io.Serializable; 
 * import com.alibaba.fastjson.JSONObject; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `Serializable`：用于实现序列化接口。 
 * - `JSONObject`：阿里巴巴的 FastJSON 库中的 JSON 对象类。 
 * - `ApiModelProperty`：用于 Swagger 文档注解。 
 * - `Getter` 和 `Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalTaskRsp implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `Serializable`：表示这个类可以被序列化。 
 * ### 成员变量 
 * ```java 
 * private String templateId; 
 * private String templateName; 
 * private String taskId; 
 * private String taskName; 
 * private String localSceneId; 
 * private String mpSceneId; 
 * private String topoId; 
 * private JSONObject topoData; 
 * private JSONObject sceneData; 
 * ``` 
 * - `templateId`：测评靶场模板ID。 
 * - `templateName`：测评名称。 
 * - `taskId`：测评任务ID。 
 * - `taskName`：测评任务名称。 
 * - `localSceneId`：当地场景ID。 
 * - `mpSceneId`：中台场景ID。 
 * - `topoId`：拓扑ID。 
 * - `topoData`：拓扑模板数据。 
 * - `sceneData`：场景数据。 
 * ### 成员变量的注释 
 * 每个成员变量都有对应的 `@ApiModelProperty` 注解，用于生成 Swagger 文档中的描述信息。 
 * ### 总结 
 * - `EvalTaskRsp` 类是一个简单的数据传输对象（DTO），用于封装评估任务的响应数据。 
 * - 它包含了与评估任务相关的多个属性，并且这些属性都可以通过 Lombok 自动生成的 getter 和 setter 方法进行访问。 
 * - 使用了 Swagger 注解来提供文档说明，方便 API 文档的生成。 
 * - 类实现了 `Serializable` 接口，支持对象的序列化操作。 
**/
@Getter
@Setter
public class EvalTaskRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 模板id
	 */
	@ApiModelProperty("测评靶场模板Id")
	private String templateId;

	/**
	 * 模板名称
	 */
	@ApiModelProperty("测评名称")
	private String templateName;

	/**
	 * 任务id
	 */
	@ApiModelProperty("测评任务Id")
	private String taskId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty("测评任务名称")
	private String taskName;

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

