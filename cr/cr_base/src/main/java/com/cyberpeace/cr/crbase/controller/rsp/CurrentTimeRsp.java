/**
 * 这段代码定义了一个名为 `CurrentTimeRsp` 的 Java 类，用于表示当前时间的响应数据结构。以下是简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.cr.crbase.controller.rsp` 包中。 
 * - 使用了 `java.util.Date` 表示时间，并通过 `io.swagger.annotations.ApiModelProperty` 注解来描述字段。 
 * 2. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段 `currentTime` 提供了描述性文档。 
 * 3. **功能**： 
 * - 类的主要目的是封装当前时间信息，提供一个标准化的 JSON 响应格式。 
 * - 字段 `currentTime` 存储当前时间，类型为 `Date`。 
 * 4. **设计意图**： 
 * - 该类可能是某个 RESTful API 接口的响应模型，用于返回当前系统时间。 
 * 总结：这是一个简单的数据传输对象（DTO），用于在系统中传递当前时间信息，结合了 Lombok 和 Swagger 的便利性。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 当前时间响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CurrentTimeRsp {

	/**
	 * 当前时间
	 */
	@ApiModelProperty(value = "当前时间")
	private Date currentTime;
}
