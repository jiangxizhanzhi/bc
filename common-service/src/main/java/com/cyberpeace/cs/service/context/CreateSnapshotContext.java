package com.cyberpeace.cs.service.context;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 创建快照上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CreateSnapshotContext` 的 Java 类，用于创建快照时的上下文信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这个类的主要作用是封装创建快照所需的上下文信息。 
 * 2. **类的属性**： 
 * - `nodeId`：表示节点的唯一标识符。 
 * - `snapshotName`：表示快照的名称。 
 * - `description`：描述快照的相关信息。 
 * 3. **注解和特性**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在网络传输或持久化存储中使用。 
 * 4. **设计意图**： 
 * - 通过将快照创建所需的信息集中在一个类中，提高了代码的可读性和可维护性。 
 * - 使用 Lombok 减少了样板代码的编写。 
 * 总结来说，这个类是一个简单的数据传输对象（DTO），用于在系统中传递创建快照所需的信息。 
**/
@Getter
@Setter
public class CreateSnapshotContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 节点id
     */
    private String nodeId;

    /**
     * 快照名称
     */
    private String snapshotName;

    /**
     * 描述
     */
    private String description;

}

