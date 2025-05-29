package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果指数响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordResultIndexRsp` 的 Java 类，用于表示评估记录的结果指数响应。以下是该类的详细分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **导入语句**： 
 * - `com.fasterxml.jackson.annotation.JsonIgnore`：用于标记不希望被序列化的字段。 
 * - `io.swagger.annotations.ApiModelProperty`：用于在 Swagger 文档中描述字段。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`EvalRecordResultIndexRsp` 
 * - 实现了 `Serializable` 接口，表明这是一个可以序列化的对象。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * ### 字段说明 
 * - **resourceId**：资源 ID，字符串类型。 
 * - **name**：名称，字符串类型。 
 * - **score**：得分，整数类型。 
 * - **algorithmId**：算法 ID，字符串类型。 
 * - **algorithmName**：算法名称，字符串类型。 
 * - **children**：子级信息，包含一个 `EvalRecordResultIndexRsp` 对象的列表。 
 * - **level**：水平，整数类型，但使用 `@JsonIgnore` 标记，不会被序列化。 
 * - **nextIds**：下一个 ID 列表，字符串类型，同样使用 `@JsonIgnore` 标记。 
 * - **parentId**：父母 ID，字符串类型，使用 `@JsonIgnore` 标记。 
 * - **mode**：模式，整数类型，表示算法是否为层次分析法。 
 * ### 功能描述 
 * - **序列化支持**：通过实现 `Serializable` 接口，该类的对象可以被序列化和反序列化。 
 * - **Swagger 文档支持**：使用 `@ApiModelProperty` 注解来生成 Swagger 文档中的字段描述。 
 * - **Lombok 支持**：使用 Lombok 的注解简化了 getter 和 setter 方法的编写。 
 * - **部分字段忽略**：通过 `@JsonIgnore` 标记，某些字段（如 `level`、`nextIds` 和 `parentId`）将不会被序列化到 JSON 中。 
 * ### 总结 
 * 这个类主要用于表示评估记录的结果指数响应，并包含了与评估相关的多个属性。它支持序列化，适用于 RESTful API 的响应数据结构。通过 Lombok 注解简化了代码，同时使用 Swagger 注解方便生成文档。 
**/
@Getter
@Setter
public class EvalRecordResultIndexRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("指标/变量项名称")
    private String name;

    /**
     * 得分
     */
    @ApiModelProperty("评估分值")
    private Integer score;

    /**
     * 算法id
     */
    @ApiModelProperty("算法ID")
    private String algorithmId;

    /**
     * 算法名称
     */
    @ApiModelProperty("算法名称")
    private String algorithmName;

    /**
     * 子级
     */
    @ApiModelProperty("下一级指标/变量项信息")
    private List<EvalRecordResultIndexRsp> children;

    /**
     * 水平
     */
    @JsonIgnore
    private Integer level;

    /**
     * 下一个id列表
     */
    @JsonIgnore
    private String nextIds;

    /**
     * 父母id
     */
    @JsonIgnore
    private String parentId;

    /**
     * 模式
     */
    @ApiModelProperty("算法是否为层次分析法")
    private Integer mode;
}

