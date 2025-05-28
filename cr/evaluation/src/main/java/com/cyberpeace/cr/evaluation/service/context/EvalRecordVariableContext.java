/**
 * 这段代码定义了一个名为 `EvalRecordVariableContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示评估记录中的变量上下文信息。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**：`EvalRecordVariableContext` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述属性的用途和是否必填。 
 * ### 属性 
 * - **resourceId**：资源 ID，字符串类型，必填。 
 * - **name**：名称，字符串类型。 
 * - **deviceId**：装置 ID，字符串类型，必填。 
 * - **probeId**：探针 ID，字符串类型，必填。 
 * ### 总结 
 * - 这个类主要包含评估记录中的变量上下文信息，如资源 ID、名称、装置 ID 和探针 ID。 
 * - 所有属性都是字符串类型，并且通过 Swagger 注解描述了每个属性的作用。 
 * - 该类实现了 `Serializable` 接口，意味着它可以被序列化，便于在网络上传输或持久化存储。 
 * 这个类的设计简洁明了，适合用于传输和存储评估记录中的变量上下文信息。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * eval record 变量上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordVariableContext implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty(value = "变量项ID", required = true)
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("变量项名称")
    private String name;

    /**
     * 装置id
     */
    @ApiModelProperty(value = "场景主机节点ID", required = true)
    private String deviceId;

    /**
     * 探针id
     */
    @ApiModelProperty(value = "选择主机可用探针ID", required = true)
    private String probeId;
}
