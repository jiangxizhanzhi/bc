/**
 * 这段代码定义了一个名为 `DeviceInfo` 的 Java 类，用于表示设备信息。以下是代码的简要分析： 
 * 1. **类的定义**： 
 * - 类名为 `DeviceInfo`，位于包 `com.cyberpeace.common.model.scene` 下。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成属性的 getter 和 setter 方法。 
 * 2. **继承与实现**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在需要将对象保存到文件、网络传输或数据库时使用。 
 * 3. **属性**： 
 * - `resourceId`：表示设备的资源 ID，类型为 `String`。 
 * - `name`：表示设备的名称，类型为 `String`。 
 * 4. **注释**： 
 * - 类上有 Javadoc 注释，说明该类的功能是存储设备信息，并标注了作者和日期。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于存储设备的基本信息，并支持序列化操作。 
**/
package com.cyberpeace.common.model.scene;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 设备信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DeviceInfo implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

}
