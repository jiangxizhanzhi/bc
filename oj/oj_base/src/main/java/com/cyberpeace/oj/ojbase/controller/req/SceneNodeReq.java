package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 场景节点请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SceneNodeReq` 的 Java 类，用于表示场景节点的请求体。以下是对其简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.ojbase.controller.req` 
 * - **功能**: 表示场景节点的请求数据模型。 
 * - **作者**: SN 
 * - **版本**: 1.0.0 
 * - **日期**: 2024年11月8日 
 * ### 类属性 
 * 1. **resourceId** 
 * - **注释**: 资源编号（课时、习题、课堂） 
 * - **注解**: 
 * - `@ApiModelProperty`: 描述字段为 "资源编号 课时、习题、课堂" 
 * - `@NotBlank`: 确保该字段不为空或空白字符串 
 * - **类型**: `String` 
 * 2. **nodeId** 
 * - **注释**: 节点编号 
 * - **注解**: 
 * - `@ApiModelProperty`: 描述字段为 "节点编号" 
 * - `@NotBlank`: 确保该字段不为空或空白字符串 
 * - **类型**: `String` 
 * 3. **roomId** 
 * - **注释**: 课程表编号 
 * - **注解**: 
 * - `@ApiModelProperty`: 描述字段为 "课程表编号" 
 * - **类型**: `String` 
 * ### 特性 
 * - **序列化**: 实现了 `Serializable` 接口，表示该类的对象可以被序列化。 
 * - **Lombok 注解**: 使用了 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - **Swagger 文档**: 使用了 `@ApiModel` 和 `@ApiModelProperty` 注解来生成 Swagger 文档，便于 API 文档的自动生成。 
 * ### 总结 
 * `SceneNodeReq` 类是一个数据传输对象 (DTO)，用于接收场景节点相关的请求参数。它包含了三个主要字段：`resourceId`、`nodeId` 和 `roomId`。这些字段通过注解进行详细的描述，并且通过 Lombok 和 Swagger 注解增强了代码的可读性和文档生成能力。 
**/
@Getter
@Setter
@ApiModel
public class SceneNodeReq implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 课时、习题、课堂")
    @NotBlank
    private String resourceId;

    /**
     * 节点id
     *
     * @see String
     */
    @ApiModelProperty("节点编号")
    @NotBlank
    private String nodeId;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("课程表编号")
    private String roomId;

}

