/**
 * 该代码定义了一个名为 `WriteUpReq` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.controller.req` 中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个用于接收写入向上请求的请求体（Request Body），主要用于处理与“房间”和“任务”相关的写入操作。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供描述信息和标记其是否必填。 
 * 3. **字段说明**： 
 * - `roomId`：表示房间的唯一标识符，是必填字段。 
 * - `missionResourceId`：表示任务资源的唯一标识符，也是必填字段。 
 * - `writeUpShow`：表示是否显示题解，是一个布尔值，且为必填字段。 
 * 4. **用途**： 
 * - 该类通常用于 API 请求中，作为接收前端传递的数据结构，支持前后端交互。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装写入向上请求所需的基本信息，便于在控制器层处理业务逻辑。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 写入向上请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class WriteUpReq {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 任务资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 写入向上显示
	 */
	@ApiModelProperty(value = "是否显示题解", required = true)
	private Boolean writeUpShow;

}
