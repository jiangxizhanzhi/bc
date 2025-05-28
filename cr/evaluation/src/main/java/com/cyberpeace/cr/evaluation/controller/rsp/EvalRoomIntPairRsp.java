/**
 * 这段代码定义了一个名为 `EvalRoomIntPairRsp` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.controller.rsp` 中。这个类主要用于表示评估房间中的整数对响应数据。以下是详细的分析： 
 * ### 类定义 
 * - **类名**: `EvalRoomIntPairRsp` 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述属性。 
 * ### 属性 
 * 1. **id** 
 * - **类型**: `Integer` 
 * - **注释**: "ID" 
 * - **Swagger 注解**: `@ApiModelProperty("ID")` 
 * 2. **label** 
 * - **类型**: `String` 
 * - **注释**: "名称" 
 * - **Swagger 注解**: `@ApiModelProperty("名称")` 
 * ### 总结 
 * - `EvalRoomIntPairRsp` 类用于封装评估房间中与整数相关的响应数据。 
 * - 该类包含两个属性：`id` 和 `label`，分别代表标识符和标签。 
 * - 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解便于 API 文档的生成和管理。 
 * 这段代码是一个典型的 POJO（Plain Old Java Object），用于在后端系统中传输数据。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估房间int双响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRoomIntPairRsp {
    /**
     * id
     */
    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 标签
     */
    @ApiModelProperty("名称")
    private String label;
}
