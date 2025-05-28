/**
 * 这段代码定义了一个名为 `ChatGroup` 的 Java 类，用于表示聊天组的信息。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类表示一个聊天组，包含用户 ID 列表、群 ID、群名称以及岗位名称等信息。 
 * 2. **注解说明**： 
 * - `@Getter` 和 `@Setter`：为所有字段提供 getter 和 setter 方法。 
 * - `@Builder`：为类生成一个流式构建器模式，方便对象实例化。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成包含所有字段的构造函数和无参构造函数。 
 * 3. **字段说明**： 
 * - `userIds`：存储参与该聊天组的用户 ID 列表。 
 * - `groupId`：唯一标识该聊天组的 ID。 
 * - `groupName`：聊天组的名称。 
 * - `roleName`：与该聊天组相关的岗位名称。 
 * 4. **用途**： 
 * - 此类可以用于在聊天系统中管理群组信息，支持通过构建器模式轻松创建实例，并且提供了基本的 getter 和 setter 方法以访问或修改属性值。 
 * 总结来说，这是一个简单的数据传输对象（DTO），旨在封装聊天组的基本信息并提供便利的操作方式。 
**/
package com.cyberpeace.common.model.ws;

import lombok.*;

import java.util.List;

/**
 * 聊天组
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroup {

	/**
	 * 用户id列表
	 */
	private List<String> userIds;
	/**
	 * 群id
	 */
	private String groupId;
	/**
	 * 群名称
	 */
	private String groupName;
	/**
	 * 岗位名称
	 */
	private String roleName;
}
