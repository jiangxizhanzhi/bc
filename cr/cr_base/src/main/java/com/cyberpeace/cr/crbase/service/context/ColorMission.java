/**
 * 这段代码定义了一个泛型类 `ColorMission<T>`，用于表示任务编排的中间模型。以下是代码的主要分析和总结： 
 * ### 分析： 
 * 1. **类定义**： 
 * - 类名为 `ColorMission`，使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 泛型 `<T>` 表示该类可以处理任意类型的数据。 
 * 2. **字段说明**： 
 * - `color`：表示颜色，类型为 `Integer`，通过 Swagger 的 `@ApiModelProperty` 注解描述其含义为“色调”。 
 * - `schemeData`：表示任务编排的方案数据，是一个泛型列表 `List<MissionSchemeObject<T>>`，其中 `MissionSchemeObject` 是一个泛型类（未在代码中展示）。 
 * - `template`：表示模板，类型为 `Integer`，通过 Swagger 注解描述其含义为“模板”。 
 * 3. **序列化支持**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，便于在网络传输或持久化时使用。 
 * 4. **Swagger 注解**： 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于生成 API 文档时描述字段的用途。 
 * ### 总结： 
 * - `ColorMission<T>` 是一个通用的任务编排中间模型类，适用于不同类型的业务场景。 
 * - 它包含颜色、任务编排方案和模板三个主要字段，并支持序列化。 
 * - 使用了 Lombok 和 Swagger 注解，简化了代码开发和文档生成的工作。 
**/
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
@Getter
@Setter
public class ColorMission<T> implements Serializable {

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 方案数据
	 */
	@ApiModelProperty(value = "任务编排")
	private List<MissionSchemeObject<T>> schemeData;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板")
	private Integer template;

}
