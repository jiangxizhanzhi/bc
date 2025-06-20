package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估房间双响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRoomPairRsp` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示评估房间双响应的数据模型。以下是详细的分析： 
 * ### 类名与包声明 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包下。 
 * - **类名**: `EvalRoomPairRsp` 
 * - 类名遵循驼峰命名法，表示“评估房间双响应”。 
 * ### 类注释 
 * - 注释说明了该类的作用：“评估房间双响应”，并提供了作者和日期信息。 
 * ### 导入的库 
 * - `io.swagger.annotations.ApiModelProperty`: 用于标记类中的字段，生成 Swagger 文档时使用。 
 * - `lombok.Getter`, `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`: Java 序列化接口，使对象能够在网络上进行传输或持久化存储。 
 * ### 类定义 
 * - 使用 `@Getter` 和 `@Setter` 注解自动生成属性的 getter 和 setter 方法。 
 * - 实现 `Serializable` 接口，表明该类的对象可以被序列化。 
 * ### 属性 
 * - `id`: 一个字符串类型的变量，表示对象的唯一标识符。 
 * - 使用 `@ApiModelProperty("ID")` 注解，用于在 Swagger 文档中描述此字段。 
 * - `label`: 一个字符串类型的变量，表示对象的标签。 
 * - 使用 `@ApiModelProperty("名称")` 注解，用于在 Swagger 文档中描述此字段。 
 * ### 总结 
 * - `EvalRoomPairRsp` 类是一个简单的数据传输对象（DTO），主要用于在前后端之间传递评估房间相关的信息。 
 * - 通过 Lombok 注解减少了样板代码，提高了代码的可读性和维护性。 
 * - 使用 Swagger 注解便于生成 API 文档，方便开发者理解和使用该类。 
**/
@Getter
@Setter
public class EvalRoomPairRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * id
     */
    @ApiModelProperty("ID")
    private String id;

    /**
     * 标签
     */
    @ApiModelProperty("名称")
    private String label;
}

