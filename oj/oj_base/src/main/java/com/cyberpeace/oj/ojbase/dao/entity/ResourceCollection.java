/**
 * 这段代码定义了一个名为 `ResourceCollection` 的实体类，用于表示资源收集相关的数据。以下是对此代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.ojbase.dao.entity` 
 * - **类名**：`ResourceCollection` 
 * - **继承关系**：继承自 `BaseTable`，这是一个基类，可能包含一些通用的数据库表字段（如创建时间、更新时间等）。 
 * ### 主要字段 
 * - **resourceId** (String)：资源ID，使用 `@TableId` 注解标记为主键，并且类型为 UUID。 
 * - **userId** (String)：用户ID。 
 * - **businessId** (String)：业务ID。 
 * - **businessType** (Integer)：业务类型，是一个枚举类型。 
 * ### 枚举类型 
 * - **BusinessType**：一个内部枚举类，实现了 `GenericEnum` 接口。枚举值包括： 
 * - `THEORY`：理论基础，代码为 0。 
 * - `REAL_VUL`：真实漏洞（已弃用），代码为 1。 
 * - `OPERATION`：实践操作，代码为 2。 
 * - `COURSE`：课程，代码为 3。 
 * - `LESSON`：课时，代码为 4。 
 * ### 注解 
 * - **`@Getter` 和 `@Setter`**：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - **`@TableName`**：MyBatis Plus 注解，指定该实体类对应的数据库表名为 `oj_resource_collection`。 
 * ### 总结 
 * 这个类主要用于表示资源收集的数据模型，包含了资源ID、用户ID、业务ID和业务类型等字段。通过继承 `BaseTable` 类，它还可能包含一些通用的数据库字段。枚举类型 `BusinessType` 定义了业务类型的几种可能值及其描述。整体设计简洁明了，符合面向对象的设计原则。 
**/
package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 资源收集
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_resource_collection")
public class ResourceCollection extends BaseTable {

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

	public enum BusinessType implements GenericEnum {

		THEORY(0, "理论基础"),
		@Deprecated
		REAL_VUL(1, "真实漏洞"), OPERATION(2, "实践操作"),
		COURSE(3, "课程"), LESSON(4, "课时");

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
