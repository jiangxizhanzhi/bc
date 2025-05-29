package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估跑算法请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRunAlgorithmReq` 的 Java 类，该类用于表示评估跑算法请求体。以下是对其主要特性的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.req` 包下。 
 * - **类名**：`EvalRunAlgorithmReq` 
 * - 类名为 `EvalRunAlgorithmReq`，表示这是一个与评估跑算法请求相关的类。 
 * - **接口实现**：`Serializable` 
 * - 该类实现了 `Serializable` 接口，说明它可以被序列化，通常用于在网络传输或持久化存储时使用。 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 提供，自动生成所有字段的 getter 和 setter 方法，简化了代码编写。 
 * - **Swagger 注解** 
 * - `@ApiModelProperty` 注解用于描述 API 中模型属性的信息，包括注释、名称等。 
 * ### 字段定义 
 * - **algorithmId** 
 * - 类型：`String` 
 * - 描述：算法的唯一标识符。 
 * - 注解：`@ApiModelProperty("算法id")` 
 * - **data** 
 * - 类型：`Object` 
 * - 描述：算法所需的数据，类型为 `Object`，表示可以是任何类型的对象。 
 * - 注解：`@ApiModelProperty("数据")` 
 * - **listData** 
 * - 类型：`Object` 
 * - 描述：列表形式的数据，类型为 `Object`，表示可以是任何类型的对象。 
 * - 注解：`@ApiModelProperty("列表数据")` 
 * - **otherArgs** 
 * - 类型：`Object` 
 * - 描述：其他额外的参数，类型为 `Object`，表示可以是任何类型的对象。 
 * - 注解：`@ApiModelProperty("其他参数")` 
 * ### 总结 
 * - **用途**：`EvalRunAlgorithmReq` 类用于封装评估跑算法请求所需的参数，包括算法 ID 和各种数据。 
 * - **灵活性**：通过使用 `Object` 类型，这个类在处理不同类型的数据时具有很大的灵活性。 
 * - **序列化支持**：通过实现 `Serializable` 接口，这个类支持序列化，便于网络传输和持久化存储。 
 * - **文档生成**：使用 Swagger 注解提供了清晰的 API 文档信息，方便开发人员理解每个字段的含义。 
 * 这个类的设计简洁明了，适合用于需要传递多种类型数据的场景。 
**/
@Getter
@Setter
public class EvalRunAlgorithmReq implements Serializable {

    /**
     * 算法id
     */
    @ApiModelProperty("算法id")
    private String algorithmId;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Object data;

    /**
     * 列出数据
     */
    @ApiModelProperty("列表数据")
    private Object listData;

    /**
     * 其他参数
     */
    @ApiModelProperty("其他参数")
    private Object otherArgs;
}

