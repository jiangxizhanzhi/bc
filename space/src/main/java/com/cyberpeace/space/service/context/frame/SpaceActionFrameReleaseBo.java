/**
 * 这段代码定义了一个名为 `SpaceActionFrameReleaseBo` 的 Java 类，继承自 `SpaceActionFrameReleaseReq`。以下是代码的主要特点和功能分析： 
 * 1. **类的功能**： 
 * - 该类是一个 BO（Business Object），用于表示空间行动框架的下发信息。 
 * - 它继承了 `SpaceActionFrameReleaseReq` 类，表明它可能扩展或补充了父类的功能。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加元数据描述，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `frameName`：框架名称，字符串类型。 
 * - `progress`：进度，字符串类型。 
 * - `released`：下发状态，默认值为 `false`，布尔类型。 
 * 4. **设计意图**： 
 * - 该类主要用于封装与空间行动框架下发相关的业务数据。 
 * - 通过继承和注解的使用，提高了代码的可读性和复用性。 
 * 总结：这是一个典型的业务对象类，用于封装空间行动框架下发的相关信息，并通过继承和注解增强了其功能和可维护性。 
**/
package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架下发Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameReleaseReq
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameReleaseBo extends SpaceActionFrameReleaseReq {

	@ApiModelProperty(value = "框架名称")
	private String frameName;

	@ApiModelProperty(value = "进度")
	private String progress;

	@ApiModelProperty(value = "下发状态")
	private Boolean released = false;
}
