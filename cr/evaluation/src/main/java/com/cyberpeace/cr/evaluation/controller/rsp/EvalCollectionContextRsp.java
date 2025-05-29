package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估收集上下文响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalCollectionContextRsp` 的 Java 类，该类用于表示评估收集上下文的响应。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - 该类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **类名**：`EvalCollectionContextRsp` 
 * - 这个类实现了 `Serializable` 接口，表明它可以被序列化，通常用于在网络上传输或持久化存储。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 库提供，用于自动生成属性的 getter 和 setter 方法。这减少了样板代码，使代码更简洁。 
 * ### 属性 
 * - **属性列表**： 
 * - `private String resourceId;`：资源 ID。 
 * - `private String name;`：名称。 
 * - `private String formatAlgorithmId;`：格式算法 ID。 
 * - `private String formatAlgorithmName;`：格式算法名称。 
 * - `private String detectionAlgorithmId;`：检波算法 ID。 
 * - `private String detectionAlgorithmName;`：检波算法名称。 
 * - `private String scoreAlgorithmId;`：得分算法 ID。 
 * - `private String scoreAlgorithmName;`：得分算法名称。 
 * ### 总结 
 * - **用途**：`EvalCollectionContextRsp` 类主要用于封装评估收集上下文的响应信息，包括资源 ID、名称以及各种算法的 ID 和名称。 
 * - **设计**：使用了 Lombok 注解来简化代码，减少样板代码。 
 * - **可序列化**：由于实现了 `Serializable` 接口，这个类可以被序列化，便于在网络上传输或持久化存储。 
 * 这段代码是一个典型的 POJO（Plain Old Java Object），适用于需要传输或存储评估上下文数据的场景。 
**/
@Getter
@Setter
public class EvalCollectionContextRsp implements Serializable {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 格式算法id
     */
    private String formatAlgorithmId;

    /**
     * 格式算法名称
     */
    private String formatAlgorithmName;

    /**
     * 检波算法id
     */
    private String detectionAlgorithmId;

    /**
     * 检波算法名称
     */
    private String detectionAlgorithmName;

    /**
     * 得分算法id
     */
    private String scoreAlgorithmId;

    /**
     * 得分算法名称
     */
    private String scoreAlgorithmName;
}

