package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;

/**
 * OJ当地场景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `OjLocalScene` 的实体类，用于表示在线评测系统（OJ）中的本地场景。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.dao.entity` 
 * - **导入的依赖**： 
 * - MyBatis Plus 相关注解 (`com.baomidou.mybatisplus.annotation`) 
 * - 自定义的基类 (`com.cyberpeace.common.baseinterface.GenericEnum`, `com.cyberpeace.common.model.BaseTable`) 
 * - Lombok 注解 (`lombok.*`) 
 * ### 类属性 
 * - **resourceId**：资源ID，使用 `@TableId` 注解标识为主键，并设置为 UUID 类型。 
 * - **userId**：用户ID，字符串类型。 
 * - **shared**：是否共享，布尔类型。 
 * - **fromWeb**：是否从网页创建，布尔类型。 
 * - **flags**：标志，字符串类型。 
 * - **businessId**：业务ID，字符串类型。 
 * - **businessType**：业务类型，整数类型。 
 * ### 内部枚举 
 * - **BusinessType**：枚举类型，表示不同的业务类型。 
 * - **LESSON**：课时，代码为 1。 
 * - **OPERATION**：实操题，代码为 2。 
 * - **CLASSROOM**：课堂，代码为 3。 
 * ### Lombok 注解 
 * - **@Getter**：为所有字段生成 getter 方法。 
 * - **@Setter**：为所有字段生成 setter 方法。 
 * - **@Builder**：允许使用构建器模式来创建对象。 
 * - **@NoArgsConstructor**：生成无参构造函数。 
 * - **@AllArgsConstructor**：生成包含所有字段的构造函数。 
 * ### 继承 
 * - `OjLocalScene` 继承自 `BaseTable`，这可能是一个通用的数据表基类。 
 * ### 表名 
 * - 使用 `@TableName("oj_local_scene")` 注解指定数据库表名为 `oj_local_scene`。 
 * ### 总结 
 * - 这个类主要描述了 OJ 系统中的本地场景信息，包括资源、用户、共享状态等。 
 * - 使用 MyBatis Plus 进行 ORM 操作，并通过 Lombok 减少样板代码。 
 * - 内部枚举 `BusinessType` 定义了不同类型的业务，方便管理和扩展。 
**/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_local_scene")
public class OjLocalScene extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 共享
	 *
	 * @see Boolean
	 */
	private Boolean shared;

	/**
	 * 从 Web
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

	/**
	 * 标志
	 *
	 * @see String
	 */
	private String flags;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	private Integer businessType;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		LESSON(1, "课时"), OPERATION(2, "实操题"), CLASSROOM(3, "课堂");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}

