package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 重命名拓扑上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RenameTopoContext` 的 Java 类，用于表示“重命名拓扑上下文”。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这个类的主要目的是封装与“重命名拓扑”相关的上下文信息。 
 * - 它包含两个关键字段：`id` 和 `name`，分别表示拓扑的唯一标识和新的名称。 
 * 2. **继承与实现**： 
 * - 类实现了 `Serializable` 接口，表明它可以被序列化（通常用于网络传输或持久化）。 
 * 3. **注解与特性**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，简化了代码。 
 * - 类的构造函数接受两个参数 (`topoId` 和 `newName`)，用于初始化 `id` 和 `name` 字段。 
 * 4. **代码结构**： 
 * - 类的设计简单明了，字段有明确的注释说明其用途。 
 * - 构造函数提供了一种方便的方式来创建对象并初始化必要的属性。 
 * 总体而言，这是一个轻量级的 POJO（Plain Old Java Object），用于传递数据。 
**/
@Getter
@Setter
public class RenameTopoContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	public RenameTopoContext(String topoId, String newName) {
		this.id = topoId;
		this.name = newName;
	}
}

