/**
 * 这段代码定义了一个名为 `DashboardInfo` 的 Java 类，用于表示一个仪表板的信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储和传输仪表板的相关信息，包括用户、朋友、群组和角色常量等数据。 
 * 2. **类的特性**： 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * 3. **字段说明**： 
 * - `mine`：表示个人用户信息，类型为 `DashboardUser`。 
 * - `friend`：表示朋友列表，类型为 `List<DashboardFriend>`。 
 * - `group`：表示群组列表，类型为 `List<DashboardGroup>`。 
 * - `roleConst`：表示角色常量列表，类型为 `List<DashboardRole>`。 
 * 4. **注解说明**： 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述信息，便于文档生成和接口测试。 
 * 总结：这是一个典型的 POJO（Plain Old Java Object）类，用于封装仪表板信息的数据结构，支持序列化和自动化的 getter/setter 方法生成。 
**/
package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;

/**
 * 仪表板信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DashboardInfo implements Serializable {

	/**
	 * 矿山
	 */
	@ApiModelProperty("个人信息")
	private DashboardUser mine;

	/**
	 * 朋友
	 */
	@ApiModelProperty("好友信息")
	private List<DashboardFriend> friend;

	/**
	 * 群
	 */
	@ApiModelProperty("群组信息")
	private List<DashboardGroup> group;

	/**
	 * 岗位常量
	 */
	@ApiModelProperty("角色常量")
	private List<DashboardRole> roleConst;
}
