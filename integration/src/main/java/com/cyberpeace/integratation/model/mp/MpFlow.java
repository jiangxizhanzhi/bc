/**
 * 这段代码定义了一个名为 `MpFlow` 的 Java 类，用于表示某种“中台流”的模型。以下是对该代码的简要分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 
 * - **导入**： 
 * - `java.io.Serializable`：表示 `MpFlow` 类实现了序列化接口。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：自定义枚举接口。 
 * ### 类定义 
 * - **类名**：`MpFlow` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：使对象可以序列化。 
 * ### 类成员变量 
 * - `id`：字符串类型，表示流的唯一标识。 
 * - `title`：字符串类型，表示流的标题。 
 * - `flowDesc`：字符串类型，表示流的描述。 
 * - `interactiveType`：整型，表示交互类型。 
 * - `resourceType`：整型，表示资源类型。 
 * - `fileName`：字符串类型，表示文件名。 
 * - `scriptFile`：字符串类型，表示脚本文件路径。 
 * - `pcapFile`：字符串类型，表示 PCAP 文件路径。 
 * - `category`：整型，表示类别。 
 * ### 内部枚举类 
 * - `resourceTypeEnum`：枚举类型，实现 `GenericEnum` 接口，定义了资源类型的可能值。 
 * - `PCAP`：表示 PCAP 包。 
 * - `SCRIPT`：表示脚本。 
 * - `interactiveTypeEnum`：枚举类型，实现 `GenericEnum` 接口，定义了交互类型的可能值。 
 * - `BACKGROUND`：表示背景流量。 
 * - `ATTACK`：表示攻击流量。 
 * ### 枚举内部结构 
 * - 每个枚举值都有一个 `code` 和 `desc` 属性。 
 * - 通过 `@Getter` 注解，这些属性可以被外部访问。 
 * ### 总结 
 * - `MpFlow` 类是一个数据传输对象（DTO），用于存储和传输流的相关信息。 
 * - 类中的枚举类型 `resourceTypeEnum` 和 `interactiveTypeEnum` 提供了固定的资源类型和交互类型的选项。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，减少了样板代码的编写。 
 * 这段代码简洁明了地定义了一个包含多个字段和枚举类型的类，适用于在系统中表示和处理流相关的数据。 
**/
package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 中台流
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpFlow implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 流描述
	 */
	private String flowDesc;

	/**
	 * 交互式类型
	 */
	private Integer interactiveType;

	/**
	 * 资源类型
	 */
	private Integer resourceType;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 脚本文件
	 */
	private String scriptFile;

	/**
	 * pcap 文件
	 */
	private String pcapFile;

	/**
	 * 类别
	 */
	private Integer category;

	public enum resourceTypeEnum implements GenericEnum {

		PCAP(0, "pcap包"), SCRIPT(1, "脚本");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		resourceTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum interactiveTypeEnum implements GenericEnum {

		BACKGROUND(0, "背景流量"), ATTACK(1, "攻击流量");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		interactiveTypeEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
