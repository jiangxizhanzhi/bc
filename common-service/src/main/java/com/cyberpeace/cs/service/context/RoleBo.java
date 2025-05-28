/**
 * 这段代码定义了一个名为 `RoleBo` 的 Java 类，用于表示岗位信息。以下是代码的简要分析： 
 * 1. **类的定义**： 
 * - 类名为 `RoleBo`，位于包 `com.cyberpeace.cs.service.context` 中。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * 2. **属性**： 
 * - `teamName`：字符串类型，表示团队名称。 
 * - `roleName`：列表类型，存储多个岗位名称。 
 * 3. **构造方法**： 
 * - 提供了一个带参数的构造方法，用于初始化 `teamName` 和 `roleName`。 
 * - 提供了一个无参构造方法，便于实例化对象。 
 * 4. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装团队和岗位的相关信息。 
**/
package com.cyberpeace.cs.service.context;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位博
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class RoleBo implements Serializable {

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 岗位名称
	 */
	private List<String> roleName;

	public RoleBo(String teamName, List<String> roleName) {
		this.teamName = teamName;
		this.roleName = roleName;
	}

	public RoleBo() {
	}
}
