package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户看到的红点提示
 *
 * @author SN
 * @date 2023/06/07
 */
/**
 * 该代码定义了一个名为 `CrBadge` 的实体类，用于表示用户在系统中看到的红点提示。以下是代码的主要分析： 
 * 1. **类与注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 属于 `com.cyberpeace.cr.crbase.dao.entity` 包。 
 * 2. **字段说明**： 
 * - `id`：主键，使用 `@TableId` 注解，类型为自增整数（`IdType.AUTO`）。 
 * - `type`：提示的类型，整数类型。 
 * - `businessId`：业务 ID，字符串类型。 
 * - `userId`：用户 ID，字符串类型。 
 * - `extra`：额外信息，字符串类型。 
 * - `createTime` 和 `updateTime`：分别记录创建时间和更新时间，使用 `@TableField` 注解，自动填充插入或更新时间。 
 * 3. **枚举类**： 
 * - 内部定义了一个枚举类 `BadgeType`，实现了 `GenericEnum` 接口。 
 * - 枚举值包括 `NOTICE`, `MISSION_RELEASE`, 和 `NEW_SUBMIT`，每个值都有对应的代码和描述。 
 * 4. **用途**： 
 * - 该类主要用于存储和管理用户界面中的红点提示信息，支持多种类型的提示，并且可以通过枚举类型进行统一管理。 
 * 总结来说，这个类是一个典型的 MyBatis-Plus 实体类，结合了 Lombok 和枚举的设计模式，便于数据库操作和业务逻辑处理。 
**/
@Getter
@Setter
public class CrBadge {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 业务id
	 */
	private String businessId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 额外
	 */
	private String extra;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public enum BadgeType implements GenericEnum {

		NOTICE(1, "notice"),
		MISSION_RELEASE(2, "任务下发提示"), NEW_SUBMIT(3, "待评估的提交");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		BadgeType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}

