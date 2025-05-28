/**
 * 这段代码定义了一个名为 `AuthContext` 的 Java 类，用于表示认证上下文信息。以下是简要分析： 
 * 1. **类的功能**： 
 * - `AuthContext` 是一个实现了 `Serializable` 接口的类，用于存储与用户认证相关的上下文信息。 
 * - 它包含了用户的组织、用户 ID、是否为 Web 认证、是否为超级管理员以及是否为普通管理员的信息。 
 * 2. **字段说明**： 
 * - `organization`：表示用户的所属组织。 
 * - `userId`：用户的唯一标识符。 
 * - `isWeb`：布尔值，表示当前认证是否是通过 Web 进行的。 
 * - `isSuperAdmin`：布尔值，表示用户是否为超级管理员。 
 * - `isAdmin`：布尔值，表示用户是否为普通管理员。 
 * 3. **Getter 和 Setter 方法**： 
 * - 所有字段都使用了 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了 getter 和 setter 方法。 
 * - 特别地，`getWeb()` 和 `getSuperAdmin()` 方法提供了额外的逻辑处理，当对应的字段为 `null` 时，默认返回 `false`。 
 * 4. **设计特点**： 
 * - 使用了 Lombok 简化代码，减少了样板代码。 
 * - 提供了默认值处理逻辑，确保字段在未初始化时不会导致空指针异常。 
 * 总结：该类是一个简单的数据传输对象（DTO），用于封装认证相关的上下文信息，并提供了基本的默认值处理功能。 
**/
package com.cyberpeace.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * auth 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class AuthContext implements Serializable {

	/**
	 * 组织
	 */
	private String organization;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 是 Web
	 */
	private Boolean isWeb;

	/**
	 * 是超级管理员
	 */
	private Boolean isSuperAdmin;

	/**
	 * 是管理员
	 */
	private Boolean isAdmin;

	public Boolean getWeb() {
		return null == isWeb ? false : isWeb;
	}

	public Boolean getSuperAdmin() {
		return null == isSuperAdmin ? false : isSuperAdmin;
	}
}
