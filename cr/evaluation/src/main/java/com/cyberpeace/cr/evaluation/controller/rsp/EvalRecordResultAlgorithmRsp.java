package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果算法响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordResultAlgorithmRsp` 的 Java 类，用于表示评估记录结果算法的响应。以下是该类的主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **实现接口**：`Serializable`，这意味着这个类的对象可以序列化为字节流，便于在网络上传输或持久化存储。 
 * ### 类属性 
 * - **resourceId**：字符串类型，表示资源的唯一标识符。 
 * - **name**：字符串类型，表示算法的名称。 
 * - **description**：字符串类型，表示算法的描述信息。 
 * - **type**：整数类型，表示算法的类型。 
 * - **algorithmLanguage**：整数类型，表示算法使用的编程语言类型。 
 * - **code**：字符串类型，表示算法的代码。 
 * - **params**：一个包含 `AlgorithmParamContext` 对象的列表，表示算法的配置参数。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法，简化了代码并减少了样板代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来提供属性的描述信息，这有助于生成 API 文档。 
 * ### 功能 
 * - 这个类主要用于封装与算法相关的评估记录结果数据，包括算法的基本信息（如名称、描述等）以及算法的配置参数。 
 * - 它通过实现 `Serializable` 接口，使得对象可以被序列化，从而可以在网络传输中使用或者持久化存储。 
 * ### 总结 
 * `EvalRecordResultAlgorithmRsp` 类是一个数据传输对象（DTO），它包含了评估记录结果算法的相关信息，并且提供了序列化的能力。这些信息在评估记录过程中可能会被用来传递算法的详细信息给前端或者其他服务。 
**/
@Getter
@Setter
public class EvalRecordResultAlgorithmRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("算法名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("算法描述")
    private String description;

    /**
     * 类型
     */
    @ApiModelProperty("算法类型")
    private Integer type;

    /**
     * 算法语言
     */
    @ApiModelProperty("算法使用语言类型")
    private Integer algorithmLanguage;

    /**
     * 代号
     */
    @ApiModelProperty("算法代码")
    private String code;

    /**
     * 参数
     */
    @ApiModelProperty("配置参数")
    private List<AlgorithmParamContext> params;
}

