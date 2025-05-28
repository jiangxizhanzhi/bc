/**
 * 这段代码定义了一个名为 `TopoSupportPartialStartContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示一个与中台拓扑相关的部分启动支持的上下文信息。下面是对其主要特性的简要分析： 
 * ### 类名及功能 
 * - **类名**: `TopoSupportPartialStartContext` 
 * - **功能**: 这个类用于存储关于某个拓扑是否支持部分启动的信息。 
 * ### 包含的属性 
 * - **id**: 一个字符串类型的属性，用来标识具体的拓扑。 
 * - **support_partial_start**: 一个布尔类型的属性，指示当前拓扑是否支持部分启动。 
 * ### 构造方法 
 * - **构造方法**: `TopoSupportPartialStartContext(String topoId, Boolean supportPartialStart)` 
 * - 该构造方法接收两个参数：`topoId` 和 `supportPartialStart`，并初始化 `id` 和 `support_partial_start` 属性。 
 * ### 注解 
 * - **@Getter** 和 **@Setter**: 这些 Lombok 注解自动生成了相应的 getter 和 setter 方法，从而简化了代码并减少了样板代码的编写。 
 * ### 实现接口 
 * - **Serializable**: 这个接口允许对象被转换成字节流，以便于在网络上传输或持久化到文件系统中。 
 * ### 总结 
 * 这个类是一个简单的数据传输对象（DTO），它包含了一些关于拓扑是否支持部分启动的信息，并且可以通过 Lombok 注解来简化代码。此外，由于实现了 `Serializable` 接口，这个类的对象可以方便地进行序列化操作。 
**/
package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用于设置中台拓扑的部分启动
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TopoSupportPartialStartContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 支持 Partial Start
	 */
	private Boolean support_partial_start;

	public TopoSupportPartialStartContext(String topoId, Boolean supportPartialStart) {
		this.id = topoId;
		this.support_partial_start = supportPartialStart;
	}

}
