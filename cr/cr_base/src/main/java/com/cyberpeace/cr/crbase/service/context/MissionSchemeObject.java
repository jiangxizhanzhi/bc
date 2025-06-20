package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务编排中间模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个泛型类 `MissionSchemeObject`，用于表示任务编排的中间模型。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个通用的中间模型，用于描述任务编排中的阶段、任务以及动态任务。 
 * 2. **泛型支持**： 
 * - 使用了泛型 `<T>`，使得 `missions` 属性可以存储任意类型的任务列表。 
 * 3. **属性说明**： 
 * - `stage`：表示任务编排的阶段名称。 
 * - `missions`：存储具体的任务列表，类型为泛型 `T`。 
 * - `dynamic`：存储动态任务列表，类型为字符串列表。 
 * 4. **注解**： 
 * - 使用了 `@ApiModelProperty` 注解，为属性提供了 Swagger 文档的描述信息。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的getter和setter方法。 
 * 5. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 总结：这是一个通用的任务编排模型类，支持动态任务和多种任务类型的定义，并且具备良好的可扩展性和文档支持。 
**/
@Getter
@Setter
public class MissionSchemeObject<T> implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stage;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务列表")
	private List<T> missions;

	/**
	 * 动态
	 */
	@ApiModelProperty(value = "动态任务列表")
	private List<String> dynamic;
}

