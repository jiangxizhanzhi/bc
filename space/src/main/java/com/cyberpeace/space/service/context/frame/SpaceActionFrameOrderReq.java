package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 空间行动框架次序请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameOrderReq` 的 Java 类，用于表示空间行动框架次序的请求体。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装与空间行动框架相关的请求参数，支持红蓝对抗场景下的操作。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据描述，便于接口文档生成。 
 * - 使用了 JSR 380（Bean Validation）的注解： 
 * - `@NotBlank`：确保字符串字段不为空或仅包含空白字符。 
 * - `@NotNull`：确保对象字段不为 null。 
 * 3. **字段说明**： 
 * - `applicationId`、`rvbId`、`frameName`、`stepName`、`stepId`、`tacticId`、`userId`、`sender`：均为必填字段，用于标识具体的框架、步骤、战法以及用户信息。 
 * - `tacticType`：战法类型字段，为可选字段（未使用 `@NotBlank`），但需要确保不为 null。 
 * - `userName`：非必填字段，用于存储用户名。 
 * 4. **设计特点**： 
 * - 类的设计遵循了良好的命名规范，字段名清晰且具有语义性。 
 * - 使用注解减少了冗长的代码，提高了开发效率。 
 * - 字段的校验规则明确，有助于在运行时进行参数验证。 
 * 总结：这是一个简洁且功能明确的请求体类，适用于需要严格参数校验的 API 场景。 
**/
@Getter
@Setter
public class SpaceActionFrameOrderReq {
    @ApiModelProperty("框架id")
    @NotBlank
    private String applicationId;

    @ApiModelProperty("红蓝对抗id")
    @NotBlank
    private String rvbId;

    @ApiModelProperty("框架名称")
    @NotBlank
    private String frameName;

    @ApiModelProperty("步骤")
    @NotBlank
    private String stepName;

    @ApiModelProperty("步骤id")
    @NotBlank
    private String stepId;

    @ApiModelProperty("战法id")
    @NotBlank
    private String tacticId;

    @ApiModelProperty("战法类型")
    @NotNull
    private Integer tacticType;

    @ApiModelProperty("用户id")
    @NotBlank
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("当前用户的user_id")
    @NotBlank
    private String sender;
}

