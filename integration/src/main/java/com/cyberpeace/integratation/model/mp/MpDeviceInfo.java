package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 中台装置信息
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpDeviceInfo` 的 Java 类，该类实现了 `Serializable` 接口，并且使用了 Lombok 库来简化 getter 和 setter 方法的生成。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.integratation.model.mp` 
 * - **类名**：`MpDeviceInfo` 
 * - **实现接口**：`Serializable`（表示这个对象可以被序列化） 
 * ### 使用的注解 
 * - **Lombok 注解**： 
 * - `@Getter`：为所有字段自动生成 getter 方法。 
 * - `@Setter`：为所有字段自动生成 setter 方法。 
 * ### 字段 
 * - **name**：字符串类型，用于存储装置的名称。 
 * - **resourceId**：字符串类型，用于存储资源 ID。 
 * ### 功能 
 * - 这个类主要用于存储中台装置的信息，包括装置的名称和关联的资源 ID。 
 * - 通过 Lombok 注解，避免了手动编写 getter 和 setter 方法，减少了代码量，提高了可读性。 
 * ### 总结 
 * 这段代码是一个简单的数据传输对象（DTO），用于在不同系统或模块之间传递装置信息。通过 Lombok 注解，简化了代码的编写，使得代码更加简洁和易于维护。 
**/
@Getter
@Setter
public class MpDeviceInfo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 资源id
	 */
	private String resourceId;
}

