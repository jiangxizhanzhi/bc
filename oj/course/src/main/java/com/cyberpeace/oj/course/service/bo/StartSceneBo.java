/**
 * 这段代码定义了一个名为 `StartSceneBo` 的类，该类位于 `com.cyberpeace.oj.course.service.bo` 包中。以下是对其主要特性和功能的简要分析： 
 * 1. **继承与实现**: 
 * - `StartSceneBo` 类实现了 `Serializable` 接口，这意味着它可以被序列化，通常用于在网络上传输或持久化存储对象。 
 * 2. **Lombok 注解**: 
 * - 使用了 `@Getter` 和 `@Setter` 注解，这两个注解由 Lombok 库提供，自动生成属性的 getter 和 setter 方法，从而简化代码。 
 * 3. **属性**: 
 * - `classroomId`: 字符串类型，表示课堂ID。 
 * - `groupId`: 字符串类型，表示群组ID。 
 * - `language`: 字符串类型，表示使用的语言。 
 * - `authContext`: `AuthContext` 类型的对象，用于保存认证上下文信息。 
 * - `userInfo`: `UserInfo` 类型的对象，用于保存用户信息。 
 * 4. **文档注释**: 
 * - 类和每个属性都有详细的 Javadoc 注释，说明了它们的作用和用途。 
 * 5. **作者与版本信息**: 
 * - 类定义中包含了作者（SN）和版本信息（1.0.0），以及最后修改日期（2024/11/08）。 
 * 总体来说，`StartSceneBo` 类是一个数据传输对象（DTO），用于在系统中传递开始场景所需的信息。它包含了课堂ID、群组ID、使用的语言、认证上下文以及用户信息等关键字段。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 开始场景博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class StartSceneBo implements Serializable {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 群id
	 *
	 * @see String
	 */
	private String groupId;

	/**
	 * 语言
	 *
	 * @see String
	 */
	private String language;

	/**
	 * auth 上下文
	 *
	 * @see AuthContext
	 */
	private AuthContext authContext;

	/**
	 * 用户信息
	 *
	 * @see UserInfo
	 */
	private UserInfo userInfo;

}
