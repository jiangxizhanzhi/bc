/**
 * 这段代码定义了一个名为 `Port` 的 Java 类，用于表示一个“港口”对象。以下是简要分析： 
 * 1. **类名与功能**： 
 * - 类名为 `Port`，注释说明它代表一个“港口”。 
 * - 它实现了 `Serializable` 接口，表明该类的对象可以被序列化（通常用于网络传输或持久化）。 
 * 2. **注解与访问修饰符**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 属性 `ip` 被定义为 `private`，通过 Lombok 自动生成公共的 getter 和 setter 方法来访问。 
 * 3. **属性**： 
 * - `ip`：表示 IP 地址，类型为 `String`。 
 * 4. **用途**： 
 * - 该类可能用于存储和管理与某个“港口”相关的 IP 地址信息，在网络通信或分布式系统中可能会用到。 
 * 总结：这是一个简单的数据类，主要用于存储和操作港口的 IP 地址信息，并支持序列化操作。 
**/
package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 港口
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class Port implements Serializable {

	/**
	 * ip地址
	 */
	private String ip;
}
