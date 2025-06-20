package com.cyberpeace.common.model.room;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Dashboard 组
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `DashboardGroup` 的 Java 类，用于表示一个仪表盘分组模型。以下是代码的主要分析和总结： 
 * ### **功能概述** 
 * `DashboardGroup` 是一个实现了 `Serializable` 接口的类，用于存储与分组相关的数据信息，包括分组名称、角色、分组编号、头像、成员列表等字段。它提供了两种构造方法来初始化对象。 
 * --- 
 * ### **主要特性** 
 * 1. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供 API 文档描述。 
 * 2. **字段说明**： 
 * - `groupname`：分组名称。 
 * - `role`：角色（岗位）。 
 * - `id`：分组编号。 
 * - `avatar`：头像。 
 * - `members`：组员编号的列表。 
 * - `roleType`：角色英文描述。 
 * - `roleCode`：角色代号（整数类型）。 
 * 3. **构造方法**： 
 * - 第一种构造方法：通过传入角色（`role`）初始化对象，其他字段赋默认值（如空字符串或默认编号）。 
 * - 第二种构造方法：支持更复杂的初始化，允许指定角色、角色代号和头像。 
 * --- 
 * ### **优点** 
 * - 使用 Lombok 减少了冗余代码，提高开发效率。 
 * - 使用 Swagger 注解增强了代码的可读性和文档化能力。 
 * - 支持多种构造方式，灵活适应不同场景需求。 
 * --- 
 * ### **潜在问题** 
 * 1. **默认值问题**： 
 * - 在第一个构造方法中，`avatar` 被硬编码为 `""`，如果需要动态设置头像，这种方式可能不够灵活。 
 * 2. **字段命名规范**： 
 * - 部分字段名称（如 `roleType` 和 `roleCode`）与其注解描述不完全一致（注解为“角色英文”和“角色code”），可能导致混淆。 
 * 3. **字段逻辑一致性**： 
 * - `groupname` 和 `id` 被直接设置为与 `role` 相同的值，这种设计是否合理需根据业务需求确认。 
 * --- 
 * ### **适用场景** 
 * 该类适用于需要表示和管理分组信息的场景，例如后台管理系统中的用户分组、角色分配等功能模块。 
**/
@Getter
@Setter
public class DashboardGroup implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 组名
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * 岗位
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * id
	 */
	@ApiModelProperty("分组编号")
	private String id;

	/**
	 * 化身
	 */
	@ApiModelProperty("头像")
	private String avatar;

	/**
	 * 成员
	 */
	@ApiModelProperty("组员编号")
	private List<String> members;

	/**
	 * 岗位类型
	 */
	@ApiModelProperty("角色英文")
	private String roleType;

	/**
	 * 岗位代号
	 */
	@ApiModelProperty("角色code")
	private Integer roleCode;

	/**
	 * Dashboard 组
	 *
	 * @param role 岗位
	 */
	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}

	/**
	 * Dashboard 组
	 *
	 * @param role     岗位
	 * @param roleCode 岗位代号
	 * @param avatar   化身
	 */
	public DashboardGroup(String role, Integer roleCode, String avatar) {
		this.role = role;
		this.avatar = avatar;
		this.groupname = role;
		this.id = role;
		this.roleCode = roleCode;
	}
}

