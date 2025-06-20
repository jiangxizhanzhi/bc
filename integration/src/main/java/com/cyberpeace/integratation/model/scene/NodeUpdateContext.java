package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点动态更新
 *
 * @author SN
 * @date 2023/05/11
 */
/**
 * 这段代码定义了一个名为 `NodeUpdateContext` 的 Java 类，该类位于 `com.cyberpeace.integratation.model.scene` 包中。以下是对其主要特性和功能的简要分析： 
 * 1. **包声明**：代码开始处声明了包名 `com.cyberpeace.integratation.model.scene`，表明这个类属于该包下的 `scene` 子包。 
 * 2. **导入语句**： 
 * - `java.io.Serializable`：表示 `NodeUpdateContext` 类实现了 `Serializable` 接口，这意味着它可以被序列化，用于在网络上传输或持久化到存储设备。 
 * - `com.alibaba.fastjson.JSONObject`：引入了 FastJSON 库中的 `JSONObject` 类，该类可以用来处理 JSON 数据。 
 * - `lombok.Getter` 和 `lombok.Setter`：通过 Lombok 库提供了自动化的 getter 和 setter 方法，简化了代码编写。 
 * 3. **类声明**：`NodeUpdateContext` 继承自 `SceneOpsContext` 类，并且实现了 `Serializable` 接口。 
 * 4. **注释**：类上有 Javadoc 注释，说明了这个类的作用是“节点动态更新”，并注明了作者和创建日期。 
 * 5. **字段**：类中只有一个字段 `update`，类型为 `JSONObject`，表示可以存储任意类型的 JSON 数据。通过 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了对该字段的访问器和修改器方法。 
 * 6. **继承关系**：`NodeUpdateContext` 类继承自 `SceneOpsContext` 类，可能从父类继承了一些通用的操作或者属性，但具体实现细节需要查看 `SceneOpsContext` 类的定义。 
 * 总结来说，`NodeUpdateContext` 类是一个用于节点动态更新操作的数据传输对象（DTO），它允许存储和传递 JSON 格式的数据，同时支持序列化以便于数据传输和持久化。 
**/
@Getter
@Setter
public class NodeUpdateContext extends SceneOpsContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 更新
	 */
	private JSONObject update;
}

