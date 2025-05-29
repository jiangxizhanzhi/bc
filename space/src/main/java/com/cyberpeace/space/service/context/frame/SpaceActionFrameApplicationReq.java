package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架行为依赖请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameApplicationReq` 的 Java 类，用于表示空间行动框架的行为依赖请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类是用于处理与空间行动框架相关的请求数据，包含红蓝对抗业务 ID (`rvbId`)、框架 ID (`frameId`) 和红蓝对抗业务名称 (`rvbName`)。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述信息，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `rvbId`：红蓝对抗业务 ID，用于标识特定的业务。 
 * - `frameId`：框架 ID，用于标识具体的框架。 
 * - `rvbName`：红蓝对抗业务名称，提供业务的可读性描述。 
 * 4. **设计目的**： 
 * 该类是一个数据传输对象（DTO），主要用于接收前端或其他服务传递的相关参数，支持红蓝对抗场景下的框架行为应用。 
 * 总结：这是一个简洁且功能明确的数据传输类，适用于空间行动框架中的行为依赖请求场景。 
**/
@Getter
@Setter
public class SpaceActionFrameApplicationReq {

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;
}

