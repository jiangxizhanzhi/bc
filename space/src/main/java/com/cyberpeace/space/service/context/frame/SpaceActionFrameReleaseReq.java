package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 空间行动框架下发请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameReleaseReq` 的 Java 类，用于表示空间行动框架下发请求的请求体。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * 该类是一个数据传输对象（DTO），主要用于接收与空间行动框架相关的请求参数。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * - 使用了 `javax.validation.constraints.NotBlank` 注解，确保某些字段在非空时必须有实际内容。 
 * 3. **字段说明**： 
 * - `applicationId`：框架实体 ID，必填。 
 * - `step`：阶段，必填。 
 * - `userId`：用户 ID，必填。 
 * - `userName`：用户名，非必填。 
 * 4. **设计目的**： 
 * 该类的主要目的是封装空间行动框架下发请求所需的必要信息，并通过注解约束字段的格式和必填性，便于后续接口调用和数据验证。 
 * 总结：这是一个简洁且功能明确的数据传输类，适用于 RESTful API 请求场景。 
**/
@Getter
@Setter
public class SpaceActionFrameReleaseReq {

	@ApiModelProperty(value = "框架实体id", required = true)
	@NotBlank
	private String applicationId;

	@ApiModelProperty(value = "阶段", required = true)
	@NotBlank
	private String step;

	@ApiModelProperty(value = "用户id", required = true)
	@NotBlank
	private String userId;

	@ApiModelProperty(value = "用户名", required = true)
	private String userName;
}

