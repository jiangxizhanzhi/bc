package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场模板公共信息模型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `BusinessTemplate` 的实体类，用于表示靶场模板的公共信息。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`BusinessTemplate` 
 * - 包名：`com.cyberpeace.cr.crbase.dao.entity` 
 * - 作者：`SN` 
 * - 创建日期：`2024/11/08` 
 * 2. **继承与注解**： 
 * - 继承自 `AuthTable`。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，指定数据库表名为 `cr_business_template`。 
 * 3. **字段说明**： 
 * - `resourceId`：资源ID，使用 MyBatis-Plus 的 `@TableId` 注解，指定为 UUID 类型。 
 * - `topoId`：拓扑ID。 
 * - `name`：名称。 
 * - `description`：描述。 
 * - `businessType`：业务类型，存储为整数。 
 * - `difficulty`：难度，存储为整数。 
 * - `createUserId`：创建用户的ID。 
 * - `logo`：图标。 
 * - `localSceneId`：用于关联后台测试场景的ID。 
 * 4. **枚举类**： 
 * - 定义了一个内部枚举类 `BusinessType`，实现了 `GenericEnum` 接口。 
 * - 枚举值包括：单兵训练、漏洞库、团队训练、红蓝对抗、装备测试、系统测试。 
 * - 每个枚举值都有对应的 `code` 和 `desc` 属性，并通过 Lombok 的 `@Getter` 注解自动提供访问器方法。 
 * 5. **用途**： 
 * - 该类主要用于描述靶场中的模板信息，支持多种业务类型的管理，适用于需要进行靶场资源管理和测试场景关联的场景。 
 * 总结：这是一个典型的实体类，结合了 MyBatis-Plus 和 Lombok 的功能，旨在简化数据库操作和代码编写，同时通过枚举类提供了业务类型的规范化管理。 
**/
@Getter
@Setter
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		TRAINING_SINGLE(1, "单兵训练"), VULN_VALIDATE(2, "漏洞库"), TRAINING_TEAM(3,
				"团队训练"), RED_VS_BLUE(4, "红蓝对抗"), WEAPON_ASSESS(6, "装备测试"), SYSTEM_ASSESS(7, "系统测试");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}

