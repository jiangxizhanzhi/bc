/**
 * 这段代码定义了一个名为 `EvalVariableGraphListRsp` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.controller.rsp` 中。以下是对其主要特性和功能的分析： 
 * ### 类和继承关系 
 * - **类名**: `EvalVariableGraphListRsp` 
 * - **包**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **继承**: 该类继承自 `EvalVariableGraphRsp`。 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，这些注解会自动生成字段的 getter 和 setter 方法。 
 * ### 字段 
 * - `private String variableName;`: 用于存储变量的名称。 
 * - `private String variableId;`: 用于存储变量的唯一标识符（ID）。 
 * ### 功能 
 * - 这个类显然是用来作为 API 响应的数据传输对象 (DTO)，用于表示与变量图相关的数据列表。具体来说，它包含变量的名称和 ID。 
 * - 由于继承自 `EvalVariableGraphRsp`，可以推断出这个类可能包含了更多关于变量图的信息，而不仅仅是变量的名称和 ID。 
 * ### 总结 
 * - `EvalVariableGraphListRsp` 是一个简单的数据传输对象，用于在 API 响应中传递变量图的相关信息。 
 * - 它提供了变量的名称和 ID，并且通过继承自 `EvalVariableGraphRsp` 类，可能还包含了其他与变量图相关的信息。 
 * - 通过使用 Lombok 的注解，减少了样板代码，使得类更简洁、易读。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Getter;
import lombok.Setter;

/**
 * 评估变量图列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalVariableGraphListRsp extends EvalVariableGraphRsp{

    /**
     * 变量名称
     */
    private String variableName;

    /**
     * 变量id
     */
    private String variableId;
}
