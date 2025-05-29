package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 中台节点状态
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpNodeStatus` 的 Java 类，该类用于表示中台节点的状态。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 
 * - **导入语句**： 
 * - `java.io.Serializable`：表明 `MpNodeStatus` 实现了 `Serializable` 接口，这意味着它可以被序列化。 
 * - `java.util.List`：表示该类使用了 `List` 接口来存储节点连线信息。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 类属性 
 * - **nodeId**：表示节点的唯一标识符，类型为 `String`。 
 * - **status**：表示节点的状态，类型为 `Integer`。 
 * - **connectionStatusList**：表示与该节点相关的连线状态列表，类型为 `List<MpConnectionStatus>`。这里的 `MpConnectionStatus` 是一个自定义的类（未在代码片段中展示）。 
 * ### Lombok 注解 
 * - `@Getter`：自动为所有属性生成 getter 方法。 
 * - `@Setter`：自动为所有属性生成 setter 方法。 
 * ### 文档注释 
 * - 类注释说明了 `MpNodeStatus` 的用途，即表示中台节点的状态。 
 * - 属性注释描述了每个属性的具体含义。 
 * ### 总结 
 * - `MpNodeStatus` 类主要用于表示和存储中台节点的状态信息。 
 * - 它包含三个属性：节点ID、节点状态以及节点连线状态列表。 
 * - 通过 Lombok 注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * - 该类实现了 `Serializable` 接口，方便进行对象的序列化操作。 
**/
@Getter
@Setter
public class MpNodeStatus implements Serializable {

    /**
     * 节点Id
     */
    private String nodeId;

    /**
     * 节点状态
     */
    private Integer status;

    /**
     * 节点连线列表
     */
    private List<MpConnectionStatus> connectionStatusList;

}

