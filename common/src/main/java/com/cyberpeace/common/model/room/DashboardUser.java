package com.cyberpeace.common.model.room;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Dashboard 用户
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `DashboardUser` 的 Java 类，用于表示一个用户的模型对象。以下是代码的主要特点和功能分析： 
 * 1. **类的功能**： 
 * - 这是一个数据传输对象（DTO），用于表示仪表盘（Dashboard）中的用户信息。 
 * - 它包含了用户的基本信息，如用户名、名称、角色、头像等。 
 * 2. **继承与接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在网络中传递或持久化存储。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，减少了冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据描述，便于生成 API 文档。 
 * 4. **字段说明**： 
 * - `username`: 用户登录名。 
 * - `name`: 用户展示名。 
 * - `role`: 用户的角色。 
 * - `roleType`: 角色类型。 
 * - `roleCode`: 角色的代号（整数类型）。 
 * - `id`: 用户的唯一标识符。 
 * - `avatar`: 用户的头像地址。 
 * - `sign`: 登录标志。 
 * - `status`: 用户的状态。 
 * 5. **链式调用方法**： 
 * - 提供了一系列以字段命名的方法（如 `id(String id)`），允许在创建对象时进行链式调用，方便构建对象实例。 
 * 6. **作者与日期**： 
 * - 代码由作者 SN 编写，并标注了编写日期为 2024 年 11 月 8 日。 
 * 总结：这是一个简洁且功能明确的用户模型类，适用于需要快速构建用户信息对象的场景，同时支持序列化和文档生成。 
**/
@Getter
@Setter
public class DashboardUser implements Serializable {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户登录名")
	private String username;

	/**
	 * 名称
	 */
	@ApiModelProperty("用户展示名")
	private String name;

	/**
	 * 岗位
	 */
	@ApiModelProperty("角色")
	private String role;

	/**
	 * 岗位类型
	 */
	@ApiModelProperty("角色类型")
	private String roleType;

	/**
	 * 岗位代号
	 */
	@ApiModelProperty("角色码")
	private Integer roleCode;

	/**
	 * id
	 */
	@ApiModelProperty("编号")
	private String id;

	/**
	 * 化身
	 */
	@ApiModelProperty("头像地址")
	private String avatar;

	/**
	 * 标志
	 */
	@ApiModelProperty("登陆")
	private String sign;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private String status;

	public DashboardUser id(String id) {
		this.id = id;
		return this;
	}

	public DashboardUser status(String status) {
		this.status = status;
		return this;
	}

	public DashboardUser sign(String sign) {
		this.sign = sign;
		return this;
	}

	public DashboardUser roleType(String roleType) {
		this.roleType = roleType;
		return this;
	}

	public DashboardUser role(String role) {
		this.role = role;
		return this;
	}

	public DashboardUser roleCode(Integer roleCode) {
		this.roleCode = roleCode;
		return this;
	}

	public DashboardUser avatar(String avatar) {
		this.avatar = avatar;
		return this;
	}

	public DashboardUser username(String username) {
		this.username = username;
		return this;
	}
}

