package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估变量数据分页响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableDataPageRsp` 的 Java 类，用于表示评估变量数据分页响应。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **类名**：`EvalVariableDataPageRsp` 
 * - **实现接口**：`Serializable`，表示该类的对象可以被序列化。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述 API 中的字段。 
 * ### 属性 
 * 1. **nodeIp** 
 * - 类型：`String` 
 * - 描述：节点 IP 地址。 
 * - 注解：`@ApiModelProperty("场景节点IP")` 
 * 2. **collectionTime** 
 * - 类型：`String` 
 * - 描述：收集时间。 
 * - 注解：`@ApiModelProperty("探针数据采集时间")` 
 * 3. **content** 
 * - 类型：`String` 
 * - 描述：内容，即探针采集的数据。 
 * - 注解：`@ApiModelProperty("探针采集数据")` 
 * ### 总结 
 * - **用途**：这个类主要用于在 API 响应中表示评估变量数据的分页结果，包括节点 IP 地址、数据采集时间和采集的数据内容。 
 * - **特性**：使用了 Lombok 注解简化了代码，使用 Swagger 注解便于文档生成和 API 文档管理。 
 * - **继承关系**：实现了 `Serializable` 接口，支持对象的序列化。 
 * 这段代码简洁明了，适合用于 RESTful API 的响应模型。 
**/
@Getter
@Setter
public class EvalVariableDataPageRsp implements Serializable {
    /**
     * 节点ip地址
     */
    @ApiModelProperty("场景节点IP")
    private String nodeIp;

    /**
     * 收集时间
     */
    @ApiModelProperty("探针数据采集时间")
    private String collectionTime;

    /**
     * 内容
     */
    @ApiModelProperty("探针采集数据")
    private String content;
}

