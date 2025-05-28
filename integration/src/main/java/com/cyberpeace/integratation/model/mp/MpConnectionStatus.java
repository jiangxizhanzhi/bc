/**
 * 这段代码定义了一个名为 `MpConnectionStatus` 的 Java 类，该类实现了 `Serializable` 接口。这个类的主要目的是表示一个中台连接的状态，并提供了两个属性：`connectionId` 和 `status`。 
 * ### 主要特点和功能： 
 * 1. **包声明**： 
 * - 代码位于 `com.cyberpeace.integratation.model.mp` 包下。 
 * 2. **导入的类**： 
 * - 导入了 `java.io.Serializable`，使对象可以序列化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成 getter 和 setter 方法。 
 * 3. **注释**： 
 * - 类上有 Javadoc 注释，说明这是一个表示中台连接状态的类。 
 * - 每个属性也有 Javadoc 注释，说明其用途。 
 * 4. **属性**： 
 * - `connectionId`：字符串类型，表示连线的 ID。 
 * - `status`：整型，表示连线的状态。 
 * 5. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：这两个注解由 Lombok 自动生成相应的 getter 和 setter 方法，减少样板代码。 
 * ### 总结 
 * - `MpConnectionStatus` 类是一个简单的数据传输对象（DTO），用于存储和传输中台连接的状态信息。 
 * - 它包含两个属性：`connectionId` 和 `status`，分别表示连线的唯一标识符和当前状态。 
 * - 使用 Lombok 注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * - 类实现了 `Serializable` 接口，意味着它可以被序列化，可能用于网络传输或持久化存储。 
**/
package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 中台连接状态
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpConnectionStatus implements Serializable {

    /**
     * 连线Id
     */
    private String connectionId;

    /**
     * 连线状态
     */
    private Integer status;

}
