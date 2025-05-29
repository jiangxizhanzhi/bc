package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 教室标志
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomSign` 的实体类，用于表示与教室签到相关的数据。以下是对其主要部分的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.dao.entity` 
 * - **类名**: `ClassroomSign` 
 * - **继承**: `BaseTable`（这是一个基类，可能包含一些通用字段或方法） 
 * - **注解**: 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * - `@Builder`: 支持构建器模式，方便对象的创建。 
 * - `@NoArgsConstructor`: 自动生成无参构造函数。 
 * - `@AllArgsConstructor`: 自动生成所有参数的构造函数。 
 * - `@TableName("oj_classroom_sign")`: 指定该实体类对应的数据库表名。 
 * ### 属性 
 * - **resourceId**: 资源ID，类型为 `String`，使用 `@TableId` 注解指定其为主键，并且类型为 UUID。 
 * - **classroomId**: 课堂ID，类型为 `String`。 
 * - **userId**: 用户ID，类型为 `String`。 
 * - **signIn**: 是否签到，类型为 `Boolean`。 
 * - **needHelp**: 是否需要帮助，类型为 `Integer`。 
 * - **signInTime**: 签到时间，类型为 `Date`。 
 * - **nodeId**: 节点ID，类型为 `String`。 
 * ### 枚举 
 * - **NEED_HELP**: 定义了四种协助状态： 
 * - `NO_HELP`: 未请求协助，值为 0。 
 * - `NEED_HELP`: 请求协助，值为 1。 
 * - `IN_HELP`: 协助中，值为 2。 
 * - `END_HELP`: 结束协助，值为 3。 
 * - 每个枚举值都有一个对应的描述信息 (`desc`) 和编码 (`code`)，并且使用了 `GenericEnum` 接口（可能是自定义接口）。 
 * ### 总结 
 * - **功能**: 该类主要用于表示教室签到的信息，包括用户是否签到、是否需要帮助等。 
 * - **数据存储**: 使用 MyBatis Plus 进行数据库操作，通过注解配置表结构。 
 * - **设计模式**: 使用了 Lombok 提供的注解来简化代码，同时使用枚举来管理状态。 
 * 总体来说，这段代码是一个典型的 Java 实体类，用于映射数据库表，并提供了相应的业务逻辑支持。 
**/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_classroom_sign")
public class ClassroomSign extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 登录
	 *
	 * @see Boolean
	 */
	private Boolean signIn;

	/**
	 * 需要帮助
	 *
	 * @see Integer
	 */
	private Integer needHelp;

	/**
	 * 签到时间
	 *
	 * @see Date
	 */
	private Date signInTime;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String nodeId;

	public enum NEED_HELP implements GenericEnum {

		/** 协助状态*/
		NO_HELP(0, "未请求协助"),
		NEED_HELP(1, "请求协助"),
		IN_HELP(2, "协助中"),
		END_HELP(3, "结束协助");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NEED_HELP(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}

