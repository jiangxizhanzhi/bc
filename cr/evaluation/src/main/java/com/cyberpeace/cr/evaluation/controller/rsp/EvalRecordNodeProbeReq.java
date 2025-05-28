/**
 * 这段代码定义了一个名为 `EvalRecordNodeProbeReq` 的 Java 类，该类用于表示评估记录节点探针的请求体。以下是对该代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.rsp; 
 * ``` 
 * 这个包声明指定了 `EvalRecordNodeProbeReq` 类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * ### 导入的类 
 * ```java 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * import java.util.List; 
 * ``` 
 * - `ApiModelProperty`：用于在 Swagger 文档中添加注解。 
 * - `Getter` 和 `Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `Serializable`：Java 接口，表示对象可以序列化。 
 * - `List`：Java 集合框架中的接口，表示有序的对象集合。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalRecordNodeProbeReq implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `implements Serializable`：表示该类的对象可以被序列化。 
 * ### 属性 
 * ```java 
 * /** 
 * * id 
 * *
 * @ApiModelProperty("场景节点ID") 
 * private String id; 
 * /** 
 * * 标签 
 * *
 * @ApiModelProperty("场景节点名称") 
 * private String label; 
 * /** 
 * * 数据 
 * *
 * @ApiModelProperty("场景节点探针数据") 
 * private List<EvalRoomPairRsp> data; 
 * ``` 
 * - `id`：表示场景节点的唯一标识符。 
 * - `label`：表示场景节点的名称。 
 * - `data`：表示场景节点探针的数据，类型为 `List<EvalRoomPairRsp>`。 
 * ### 总结 
 * - `EvalRecordNodeProbeReq` 类是一个用于表示评估记录节点探针请求体的数据传输对象（DTO）。 
 * - 该类包含三个属性：`id`、`label` 和 `data`，分别表示场景节点的 ID、名称和探针数据。 
 * - 通过 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了属性的访问器方法。 
 * - 该类实现了 `Serializable` 接口，支持对象的序列化。 
 * - 使用 Swagger 的 `@ApiModelProperty` 注解来描述属性，以便生成 API 文档。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录节点探针请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordNodeProbeReq implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("场景节点ID")
    private String id;

    /**
     * 标签
     */
    @ApiModelProperty("场景节点名称")
    private String label;

    /**
     * 数据
     */
    @ApiModelProperty("场景节点探针数据")
    private List<EvalRoomPairRsp> data;
}
