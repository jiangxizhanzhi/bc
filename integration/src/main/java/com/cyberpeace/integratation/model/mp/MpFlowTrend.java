/**
 * 这段代码定义了一个名为 `MpFlowTrend` 的 Java 类，用于表示中台流趋势的数据模型。以下是该类的详细分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 
 * - 表示这个类属于 `com.cyberpeace.integratation.model.mp` 这个包。 
 * - **导入语句**： 
 * - `java.io.Serializable`：表示该类实现了 `Serializable` 接口，支持对象的序列化。 
 * - `java.util.Date`：表示使用了 `Date` 类来表示时间。 
 * - `io.swagger.annotations.ApiModelProperty`：表示使用 Swagger 注解来描述模型属性。 
 * - `lombok.Getter` 和 `lombok.Setter`：表示使用 Lombok 库来自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * - **类名**：`MpFlowTrend` 
 * - 该类实现了 `Serializable` 接口。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成所有字段的 getter 和 setter 方法。 
 * ### 字段 
 * - **dataSize** 
 * - 类型：`Integer` 
 * - 注释：表示流量大小。 
 * - 注解：`@ApiModelProperty("流量大小")`，使用 Swagger 注解描述该字段的含义。 
 * - **time** 
 * - 类型：`Date` 
 * - 注释：表示时间。 
 * - 注解：`@ApiModelProperty("时间")`，使用 Swagger 注解描述该字段的含义。 
 * ### 总结 
 * - **用途**：该类用于表示中台流趋势的数据模型，包含两个字段：流量大小 (`dataSize`) 和时间 (`time`)。 
 * - **技术栈**：使用了 Lombok 来简化代码，并且通过 Swagger 注解来描述模型属性，方便 API 文档生成。 
 * - **可扩展性**：如果需要添加更多字段或功能，可以很方便地进行扩展。 
 * 总体来说，这是一个简洁且功能明确的数据模型类，适用于需要记录流量趋势的应用场景。 
**/
package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台流趋势
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpFlowTrend implements Serializable {

	/**
	 * 流量大小
	 */
	@ApiModelProperty("流量大小")
	private Integer dataSize;

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private Date time;

}
