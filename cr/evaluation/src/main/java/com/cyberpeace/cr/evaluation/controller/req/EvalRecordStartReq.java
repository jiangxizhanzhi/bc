package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估记录开始请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordStartReq` 的 Java 类，该类用于表示评估记录开始请求的数据结构。以下是对该代码的简要分析： 
 * ### 类名和包声明 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中。 
 * - **类名**：`EvalRecordStartReq` 
 * - 类名表明这是一个与评估记录开始请求相关的数据传输对象（DTO）。 
 * ### 导入的类 
 * - **`io.swagger.annotations.ApiModelProperty`** 
 * - 用于标注字段，以便在生成的 Swagger 文档中显示描述信息。 
 * - **`lombok.Getter` 和 `lombok.Setter`** 
 * - Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - **`java.io.Serializable`** 
 * - 表明该类可以序列化，通常用于在网络上传输或持久化时使用。 
 * ### 类的注释 
 * - 类注释说明了该类的作用：评估记录开始请求体。 
 * ### 字段 
 * - **`resourceId`** 
 * - 类型：`String` 
 * - 注释说明：这是评估记录的资源 ID。 
 * - 标注：`@ApiModelProperty("评估id")`，表示在 Swagger 文档中将此字段描述为“评估id”。 
 * ### Getter 和 Setter 方法 
 * - 由于使用了 `@Getter` 和 `@Setter` 注解，Lombok 会自动生成这些方法，因此无需手动编写。 
 * ### 总结 
 * - `EvalRecordStartReq` 是一个简单的数据传输对象（DTO），用于封装评估记录开始请求所需的数据。 
 * - 它包含一个字符串类型的字段 `resourceId`，用于存储评估记录的资源 ID。 
 * - 该类实现了 `Serializable` 接口，使其可以在网络上传输或持久化。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
**/
@Getter
@Setter
public class EvalRecordStartReq implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("评估id")
    private String resourceId;
}

