/**
 * 该代码定义了一个名为 `MissionDynamicRsp` 的 Java 类，用于表示任务动态响应的数据结构。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 该类是一个响应对象，用于封装任务的动态信息，包括统计信息和动态数据。 
 * 2. **依赖的类和注解**： 
 * - 使用了 `List` 集合来存储统计信息 (`StatisticalInfo`) 和动态数据 (`DynamicData`)。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供描述性信息，便于文档生成。 
 * 3. **主要字段**： 
 * - `statisticalInfo`：存储统计信息的列表，每个元素是 `StatisticalInfo` 对象。 
 * - `dynamicData`：存储动态数据的列表，每个元素是 `DynamicData` 对象。 
 * 4. **设计目的**： 
 * - 该类的设计目的是为了在任务动态响应中传递统计信息和动态数据，便于前端或其他模块使用。 
 * 5. **可扩展性**： 
 * - 通过使用泛型 `List`，可以轻松扩展支持更多的统计或动态数据类型。 
 * 总结：这是一个简单的 DTO（数据传输对象）类，主要用于封装任务的动态信息，方便在系统内部或外部进行数据传递。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务动态响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionDynamicRsp {

	/**
	 * 统计信息
	 */
	@ApiModelProperty(value = "统计信息")
	private List<StatisticalInfo> statisticalInfo;

	/**
	 * 动态数据
	 */
	@ApiModelProperty(value = "动态数据")
	private List<DynamicData> dynamicData;
}
