/**
 * 这段代码定义了一个名为 `SystemProperty` 的 Java 实体类，用于表示系统属性。以下是代码的简要分析： 
 * 1. **类定义**： 
 * - 类 `SystemProperty` 继承了 `Serializable` 接口，表明它可以被序列化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成 getter 和 setter 方法。 
 * 2. **表名映射**： 
 * - 使用了 MyBatis-Plus 的注解 `@TableName("system_property")` 将该类映射到数据库中的 `system_property` 表。 
 * 3. **字段定义**： 
 * - `name`：系统属性的名称。 
 * - `desc`：系统属性的描述，由于字段名为 `desc` 是 Java 的保留关键字，因此使用了 `@TableField("`desc`")` 进行特殊处理。 
 * - `content`：系统属性的内容。 
 * 4. **枚举类**： 
 * - 定义了一个内部枚举类 `NameEnum`，实现了 `GenericEnum` 接口。 
 * - 枚举类包含两个常量：`EXPORT_BUILTIN` 和 `IS_SHOW_SITUATION`，每个常量都有一个对应的 `code` 和 `desc`。 
 * 5. **功能**： 
 * - 该类主要用于系统属性的管理，支持通过 MyBatis-Plus 进行数据库操作，并且通过枚举类提供了标准化的属性名称。 
 * 总结：这是一个典型的实体类，结合了 MyBatis-Plus 的表映射功能和枚举类的标准化定义，适用于系统属性的管理和数据库操作。 
**/
package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统属性
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("system_property")
public class SystemProperty implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	@TableField("`desc`")
	private String desc;

	/**
	 * 内容
	 */
	private String content;

	public enum NameEnum implements GenericEnum {

		EXPORT_BUILTIN(1, "export_builtin"),
		IS_SHOW_SITUATION(2, "isShowSituation");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		NameEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
